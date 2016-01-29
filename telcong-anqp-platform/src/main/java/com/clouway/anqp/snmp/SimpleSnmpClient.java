package com.clouway.anqp.snmp;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 */
final class SimpleSnmpClient implements SnmpClient {
  private static final Logger log = LoggerFactory.getLogger(SimpleSnmpClient.class);

  private static final String TRANSPORT_PROTOCOL = "UDP";
  private static final Integer SNMP_VERSION = SnmpConstants.version2c;
  private static final Integer MAX_RETRY_COUNT = 3;

  private final String host;
  private final Community community;
  private final Integer port;
  private Snmp snmp;

  public SimpleSnmpClient(String host, Community community, Integer port) {
    this.host = host;
    this.community = community;
    this.port = port;
  }


  @Override
  public void start() throws IOException {
    TransportMapping transport = new DefaultUdpTransportMapping();
    snmp = new Snmp(transport);

    // Do not forget this line!
    transport.listen();
  }

  public Object get(OID oid, Object defaultValue) {
    // If target network device is overloaded is possible request to timeout, and we will not receive response.
    // We should try again to send SNMP GET
    // TODO(Emil Georgiev) this is simplest way to resolve problem. In feature we can implement
    // Circuit Breaker pattern (http://martinfowler.com/bliki/CircuitBreaker.html) or something else.
    for (int retry = 0; retry < MAX_RETRY_COUNT; retry++) {
      try {
        Object o = doGet(oid);
        if (o != null) {
          return o;
        }
      } catch (Exception e) {
        log.debug("Get Exception Caught: ", e);
        log.debug("We are retrying the request again...");
      }

      sleep(500);
    }

    log.info("We can't retrieve value from OID: {} and because of that we use default value: {}", oid, defaultValue);

    return defaultValue;
  }

  @Override
  public List<Variable> getBulk(List<OID> oids) {
    List<Variable> variables = Lists.newArrayList();

    PDU requestPDU = new PDU();

    for (OID oid : oids) {
      requestPDU.addOID(new VariableBinding(oid));
    }

    try {
      long start = System.currentTimeMillis();
      for (OID oid : oids) {
        log.debug("Getting OIDs: {}", oid);
      }
      ResponseEvent event = snmp.send(requestPDU, getTarget(community.read));
      long end = System.currentTimeMillis();
      log.debug("(GET) send executed in {} ms", (end - start));

      PDU responsePdu = event.getResponse();

      if (responsePdu != null) {
        Vector variableBindings = responsePdu.getVariableBindings();

        for (Object binding : variableBindings) {
          variables.add(((VariableBinding) binding).getVariable());
        }
      }

    } catch (IOException e) {
      log.debug("Error occurred while getting multiple oids {}", e);
    }

    return variables;
  }

  @Override
  public VariableBinding setList(List<SetEntry> entries) {
    try {
      // create PDU
      PDU pdu = new PDU();
      pdu.setType(PDU.SET);
      for (SetEntry entry : entries) {
        log.info("Sending OID: " + entry.oid + " - " + entry.variable);
        pdu.add(new VariableBinding(entry.oid, entry.variable));
      }
      pdu.setNonRepeaters(0);

      ResponseEvent resp = null;
      for (int i = 0; i < 10; i++) {
        resp = snmp.set(pdu, getTarget(community.write));
        PDU respPDU = resp.getResponse();
        if (respPDU == null) {
          log.warn("SNMP Timeout occured. Retrying... (" + (i + 1) + ")");
        } else {
          break;
        }
        sleep(100);
      }

      if (resp == null) {
        return null;
      }

      PDU respPDU = resp.getResponse();
      if (respPDU == null) {
        log.warn("SNMP Timeout occurred.");
      } else {
        Vector vbs = respPDU.getVariableBindings();
        if (vbs.size() > 0) {
          VariableBinding vb = (VariableBinding) vbs.get(0);

          log.debug("Response: " + vb.getOid() + " - " + vb.getVariable());

          sleep(150);
          return vb;

        }
      }
    } catch (Exception ex) {
      log.info("Exception caught", ex);
    }

    sleep(150);

    return null;
  }

  public boolean set(SetEntry entry) {
    log.debug("Sending OID: " + entry.oid);
    log.debug("Sending value: " + entry.variable);
    boolean ret = false;

    try {
      // create PDU
      PDU pdu = new PDU();
      pdu.setType(PDU.SET);
      pdu.add(new VariableBinding(entry.oid, entry.variable));
      pdu.setNonRepeaters(0);

      ResponseEvent resp = null;
      for (int retry = 0; retry < 10; retry++) {
        resp = snmp.set(pdu, getTarget(community.write));
        PDU respPDU = resp.getResponse();
        if (respPDU == null) {
          log.warn("SNMP Timeout occurred. Retrying...(" + (retry + 1) + ")");
        } else {
          break;
        }
      }

      // no response was returned after retries, so we are returning
      if (resp == null) {
        return false;
      }

      PDU respPDU = resp.getResponse();
      if (respPDU == null) {
        log.warn("SNMP Timeout occurred.");
      } else {
        Vector vbs = respPDU.getVariableBindings();
        if (vbs.size() > 0) {
          VariableBinding vb = (VariableBinding) vbs.get(0);
          ret = !vb.isException();
        } else {
          ret = false;
        }
      }
    } catch (Exception ex) {
      log.info("Set Exception caught", ex);
    }
    sleep(150);
    return ret;
  }

  public List<VariableBinding> walk(OID oid) {
    List<VariableBinding> ret = new ArrayList<VariableBinding>();

    log.debug("{} - Walking...", oid.toString());

    PDU requestPDU = new PDU();
    requestPDU.add(new VariableBinding(oid));
    requestPDU.setType(PDU.GETNEXT);

    try {
      boolean finished = false;

      while (!finished) {
        VariableBinding vb = null;

        ResponseEvent respEvt = snmp.send(requestPDU, getTarget(community.read));

        PDU responsePDU = respEvt.getResponse();
        if (responsePDU != null) {
          vb = responsePDU.get(0);
        }

        if (responsePDU == null) {
          finished = true;
        } else if (responsePDU.getErrorStatus() != 0) {
          finished = true;
        } else if (vb.getOid() == null) {
          finished = true;
        } else if (vb.getOid().size() < oid.size()) {
          finished = true;
        } else if (oid.leftMostCompare(oid.size(), vb.getOid()) != 0) {
          finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
          finished = true;
        } else if (vb.getOid().compareTo(oid) <= 0) {
          finished = true;
        } else {
          ret.add(vb);

          // Set up the variable binding for the next entry.
          requestPDU.setRequestID(new Integer32(0));
          requestPDU.set(0, vb);
        }
      }
    } catch (IOException e) {
      log.debug("Walk Exception Caught", e);
    }
    sleep(150);
    return ret;
  }

  private Object doGet(OID oid) throws IOException {
    Object ret = null;

    // create PDU
    PDU pdu = new PDU();
    pdu.setType(PDU.GET);
    pdu.addOID(new VariableBinding(oid));
    pdu.setNonRepeaters(0);

    long start = System.currentTimeMillis();
    log.debug("Getting OID: {}", oid);
    ResponseEvent resp = snmp.send(pdu, getTarget(community.read));
    long end = System.currentTimeMillis();
    log.debug("(GET) send executed in {} ms", (end - start));
    PDU respPDU = resp.getResponse();

    if (respPDU == null) {
      return null;
    }

    Vector vbs = respPDU.getVariableBindings();
    if (vbs.size() > 0) {
      VariableBinding vb = (VariableBinding) vbs.get(0);
      ret = vb.getVariable();
    } else {
      ret = null;
    }

    sleep(150);

    return ret;
  }

  private void sleep(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private Target getTarget(String community) {
    Address targetAddress = GenericAddress.parse(TRANSPORT_PROTOCOL + ":" + host + "/" + port);
    CommunityTarget target = new CommunityTarget();

    target.setCommunity(new OctetString(community));
    target.setVersion(SNMP_VERSION);
    target.setRetries(1);
    target.setAddress(targetAddress);
    target.setTimeout(500l);

    return target;
  }
}
