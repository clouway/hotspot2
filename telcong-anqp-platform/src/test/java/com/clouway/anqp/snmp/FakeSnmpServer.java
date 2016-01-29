package com.clouway.anqp.snmp;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import org.snmp4j.*;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 */
class FakeSnmpServer extends AbstractExecutionThreadService {
  private Snmp snmp;
  private Integer port;
  private TransportMapping transport;
  private Map<OID, VariableBinding> requestResponseBindings = newHashMap();

  FakeSnmpServer(Integer port) {
    this.port = port;
  }

  void pretendThatConfigurationWorkflowWillBe(RequestResponseBinding... bindings) {
    for (RequestResponseBinding binding : bindings) {
      requestResponseBindings.put(binding.oid, binding.variableBinding);
    }
  }

  @Override
  protected void run() throws Exception {
    MessageDispatcher dispatcher = new MessageDispatcherImpl();
    dispatcher.addMessageProcessingModel(new MPv2c());

    UdpAddress listenAddress = new UdpAddress(port);
    transport = new DefaultUdpTransportMapping(listenAddress, true);
    transport.listen();
    snmp = new Snmp(dispatcher, transport);
    snmp.addCommandResponder(new CommandResponder() {
      @Override
      public void processPdu(CommandResponderEvent event) {
        PDU pdu = event.getPDU();
        PDU resp = new PDU();
        VariableBinding response;

        for (VariableBinding v : pdu.getVariableBindings()) {
          OID oid = v.getOid();

          response = requestResponseBindings.get(oid);
          resp.add(response);
        }

        try {
          CommunityTarget comm = new CommunityTarget(event.getPeerAddress(), new OctetString(event.getSecurityName()));
          comm.setSecurityLevel(event.getSecurityLevel());
          comm.setSecurityModel(event.getSecurityModel());

          resp.setType(PDU.RESPONSE);
          resp.setRequestID(pdu.getRequestID());

          snmp.send(resp, comm);
        } catch (IOException e) {
          System.err.println(String.format("Unable to send response PDU! (%s)", e.getMessage()));
        }
        event.setProcessed(true);
      }
    });
  }

  @Override
  protected void triggerShutdown() {
    try {
      transport.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}