package com.clouway.anqp.snmp;

import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

class MibRepository {
  static class MibEntry {
    final String oid;
    final String value;

    public MibEntry(String oid, String value) {
      this.oid = oid;
      this.value = value;
    }
  }

  private Map<String, String> nameToOid = Maps.newHashMap();
  private Map<String, String> oidToName = Maps.newHashMap();

  public MibRepository() {
    doLoad();
  }

  private void doLoad() {
    Type mibEntryType = new TypeToken<List<MibEntry>>() {
    }.getType();
    Gson gson = new Gson();

    try {
      String json = new String(ByteStreams.toByteArray(MibRepository.class.getResourceAsStream("Mibs.json")));
      List<MibEntry> entries = gson.fromJson(json, mibEntryType);

      for (MibEntry entry : entries) {
        nameToOid.put(entry.value, entry.oid);
        oidToName.put(entry.oid, entry.value);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String findOid(String name) {
    return nameToOid.get(name);
  }

  public String findName(String oid) {
    return oidToName.get(oid);
  }
}