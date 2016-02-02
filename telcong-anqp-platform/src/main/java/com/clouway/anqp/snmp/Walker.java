package com.clouway.anqp.snmp;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import java.util.List;
import java.util.Map;

import static com.clouway.anqp.snmp.Walker.Binding.unknownBinding;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.parseLong;

/**
 */
class Walker {
  private final OID oid;

  public Walker(OID oid) {
    this.oid = oid;
  }

  public BindingCollection walk(SnmpClient client) {
    List<Binding> bindings = newArrayList();
    List<VariableBinding> variables = client.walk(oid);

    for (VariableBinding binding : variables) {
      Long id = binding.getOid().lastUnsigned();
      OID oid = binding.getOid();
      Value value = new Value(binding.toValueString());

      bindings.add(new Binding(id, oid, value));
    }

    return new BindingCollection(bindings);
  }

  public static class BindingCollection {
    private final Map<OID, Binding> oidToBinding = newHashMap();

    BindingCollection(List<Binding> idToBinding) {
      for (Binding binding : idToBinding) {
        this.oidToBinding.put(binding.oid, binding);
      }
    }

    public Binding get(OID oid) {
      for (Map.Entry<OID, Binding> binding : oidToBinding.entrySet()) {
        String o1 = oid.toString();
        String o2 = binding.getKey().toString();

        if (o2.contains(o1)) {
          return binding.getValue();
        }
      }

      return unknownBinding();
    }
  }

  public static class Binding {
    public final Long id;
    public final OID oid;
    public final Value value;

    static Binding unknownBinding() {
      return new Binding(0l, new OID(""), new Value("0"));
    }

    Binding(Long id, OID oid, Value value) {
      this.id = id;
      this.oid = oid;
      this.value = value;
    }
  }

  public static class Value {
    private final String value;

    Value(String value) {
      this.value = value;
    }

    public String getString() {
      return value;
    }

    public Long getLong() {
      return parseLong(value);
    }

    public Boolean getBoolean() {
      return "1".equals(value);
    }
  }
}