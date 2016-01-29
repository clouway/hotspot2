package com.clouway.anqp.snmp;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface SnmpClient {

  Object get(OID oid, Object defaultValue);

  List<Variable> getBulk(List<OID> oids);

  void start() throws IOException;

  VariableBinding setList(List<SetEntry> entries);

  boolean set(SetEntry entry);

  List<VariableBinding> walk(OID oid);
}
