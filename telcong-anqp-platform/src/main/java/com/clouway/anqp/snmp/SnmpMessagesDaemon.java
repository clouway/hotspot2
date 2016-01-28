package com.clouway.anqp.snmp;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AbstractIdleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.primitives.Ints.asList;

/**
 * Snmp Messages Daemon.
 */
class SnmpMessagesDaemon extends AbstractIdleService {
  private final Logger log = LoggerFactory.getLogger(SnmpMessagesDaemon.class);

  class Worker extends Thread {
    private LinkedBlockingQueue<CommandResponderEvent> eventsQueue;

    public Worker(LinkedBlockingQueue<CommandResponderEvent> eventsQueue) {
      this.eventsQueue = eventsQueue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          CommandResponderEvent event = eventsQueue.take();
          Address peerAddress = event.getPeerAddress();
          PDU pdu = event.getPDU();
          OID oid = getTrapOID(pdu);

          logReceivedMessage(pdu);

          String sourceAddress = peerAddress.toString().split("\\/")[0];
          String community = new String(event.getSecurityName());

          messageListener.onMessageReceived(pdu, oid, sourceAddress, community);

        } catch (InterruptedException e) {
          log.debug("Message Listener thread was terminated", e);
          break;
        } catch (Exception e) {
          log.debug("An exception was thrown", e);
        }
      }
    }
  }

  private final Map<String, LinkedBlockingQueue<CommandResponderEvent>> queues = Maps.newConcurrentMap();

  private final MibRepository mibRepository;
  private final SnmpMessageListener messageListener;
  private final Integer port;

  private Snmp snmp;
  private TransportMapping<UdpAddress> transport;

  public SnmpMessagesDaemon(MibRepository mibRepository, SnmpMessageListener messageListener, Integer port) {
    this.mibRepository = mibRepository;
    this.messageListener = messageListener;
    this.port = port;
  }

  @Override
  protected void startUp() throws Exception {
    ThreadPool threadPool = ThreadPool.create("Trap", 32);

    MessageDispatcher dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

    UdpAddress listenAddress = new UdpAddress(port);
    transport = new DefaultUdpTransportMapping(listenAddress);

    snmp = new Snmp(dispatcher, transport);
    snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
    snmp.addCommandResponder(new CommandResponder() {
      public void processPdu(final CommandResponderEvent event) {

        Address peerAddress = event.getPeerAddress();
        String sourceAddress = peerAddress.toString().split("\\/")[0];
        String community = new String(event.getSecurityName());
        OID oid = getTrapOID(event.getPDU());

        // Really rare case, which should never happen
        if (oid == null) {
          log.error("TRAP OID was null, so handling is skipped.");
          return;
        }

        LinkedBlockingQueue<CommandResponderEvent> queue;

        if (queues.get(sourceAddress) == null) {
          queue = new LinkedBlockingQueue<>();
          queue.add(event);
          queues.put(sourceAddress, queue);
          Thread worker = new Thread(new Worker(queue));
          worker.start();
        } else {
          queue = queues.get(sourceAddress);
          queue.add(event);
        }

        String trapName = mibRepository.findName(oid.toString());

        log.info("Source - {}, Community - {}, TRAP OID ({}): {}", new Object[]{sourceAddress, community, oid.toString(), trapName});
        log.info("TRAP Queue (" + sourceAddress + ") Size is: " + queue.size());
      }
    });

    USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
    SecurityModels.getInstance().addSecurityModel(usm);

    try {
      log.info("SNMP Backend was started on port: {}", port);
      snmp.listen();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void shutDown() throws Exception {
    try {
      if (transport != null) {
        transport.close();
      }
      if (snmp != null) {
        snmp.close();
      }

      log.info("SNMP Backend was stopped.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private OID getTrapOID(PDU pdu) {
    for (VariableBinding binding : pdu.getVariableBindings()) {
      if (binding.getVariable() instanceof OID) {
        return (OID) binding.getVariable();
      }
    }

    return null;
  }

  private void logReceivedMessage(PDU pdu) {
    StringBuilder message = new StringBuilder();

    for (VariableBinding binding : pdu.getVariableBindings()) {
      int[] value = binding.getOid().getValue();

      Boolean found = false;
      LinkedList<Integer> values = newLinkedList(asList(value));

      while (values.size() > 0) {
        String oidValue = Joiner.on(".").join(values);
        String oidName = mibRepository.findName(oidValue);

        if (oidName != null) {
          String msg = oidName + " - " + binding.getOid().toString() + " - " + binding.getVariable().getClass() + " - " + binding.getVariable();
          message.append(msg).append("\n");
          found = true;
          break;
        }

        values.removeLast();
      }

      if (!found) {
        log.debug("Name cannot be found for OID: " + binding.getOid());
      }
    }

    log.debug(message.toString());
  }
}