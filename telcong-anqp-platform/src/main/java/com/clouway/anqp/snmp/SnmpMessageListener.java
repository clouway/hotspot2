package com.clouway.anqp.snmp;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;

/**
 * SnmpMessageListener listens for SNMP messages that are coming via SNMP.
 */
interface SnmpMessageListener {

  /**
   * Called when new SnmpMessage was received.
   *
   * @param pdu           the pdu that contains message information
   * @param oid           the oid of the trap that is coming or null if trap oid cannot be retrieved
   * @param sourceAddress ip of the network device
   * @param community     the community which is received from the trap
   */
  void onMessageReceived(PDU pdu, OID oid, String sourceAddress, String community);
}