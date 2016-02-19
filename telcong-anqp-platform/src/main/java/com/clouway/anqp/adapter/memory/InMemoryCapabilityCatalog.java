package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.Capability;
import com.clouway.anqp.CapabilityCatalog;
import com.clouway.anqp.CapabilityList;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

class InMemoryCapabilityCatalog implements CapabilityCatalog {
  private final Map<Integer, Capability> capabilities;

  public InMemoryCapabilityCatalog(Map<Integer, Capability> capabilities) {
    this.capabilities = ImmutableMap.copyOf(capabilities);
  }

  @Override
  public CapabilityList findAll() {
    List<Capability> capabilities = Lists.newArrayList(this.capabilities.values());

    return new CapabilityList(capabilities);
  }

  @Override
  public Optional<Capability> findById(Integer id) {
    Capability capability = capabilities.get(id);

    return Optional.fromNullable(capability);
  }

  @Override
  public CapabilityList findByIds(List<Integer> ids) {
    ids = Lists.newArrayList(new TreeSet<>(ids));

    List<Capability> capabilities = Lists.newArrayList();

    for (Integer id : ids) {
      if (this.capabilities.containsKey(id)) {
        Capability capability = this.capabilities.get(id);

        capabilities.add(capability);
      }
    }

    return new CapabilityList(capabilities);
  }

  @Override
  public boolean isSupported(Integer id) {
    return capabilities.containsKey(id);
  }
}
