package com.clouway.anqp.snmp;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
class SimpleSnmpMessageListener implements SnmpMessageListener {
  @Override
  public void onMessageReceived(PDU pdu, OID oid, String sourceAddress, String community) {

  }
}