package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.IpTypeCatalog;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

class InMemoryIpTypeCatalog implements IpTypeCatalog {
  private final Map<String, Integer> types;

  public InMemoryIpTypeCatalog(Map<String, Integer> types) {
    this.types = ImmutableMap.copyOf(types);
  }

  @Override
  public List<String> getAll() {
    return Lists.newArrayList(types.keySet());
  }

  @Override
  public Optional<Integer> findId(String type) {
    return Optional.fromNullable(types.get(type));
  }

  @Override
  public boolean isSupported(String type) {
    return types.containsKey(type);
  }
}
