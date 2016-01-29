package com.clouway.anqp.snmp;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

/**
 */
public final class SetEntry {
  public final OID oid;
  public final Variable variable;

  public SetEntry(OID oid, Variable variable) {
    this.oid = oid;
    this.variable = variable;
  }

  @Override
  public String toString() {
    return "SetEntry{" +
            "oid=" + oid +
            ", variable=" + variable +
            '}';
  }
}