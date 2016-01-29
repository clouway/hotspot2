package com.clouway.anqp.snmp;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

/**
 */
class RequestResponseBinding {
  final OID oid;
  final VariableBinding variableBinding;

  RequestResponseBinding(OID oid, VariableBinding variableBinding) {
    this.oid = oid;
    this.variableBinding = variableBinding;
  }
}
