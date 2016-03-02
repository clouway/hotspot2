package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.IPv4.Availability;
import com.clouway.anqp.IPv4AvailabilityCatalog;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

class InMemoryIPv4AvailabilityCatalog implements IPv4AvailabilityCatalog {
  private final Map<String, Availability> availabilities;

  public InMemoryIPv4AvailabilityCatalog(Availability... availabilities) {
    Map<String, Availability> iPv4Map = Maps.newLinkedHashMap();

    for (Availability availability : availabilities) {
      iPv4Map.put(availability.name(), availability);
    }

    this.availabilities = ImmutableMap.copyOf(iPv4Map);
  }

  @Override
  public List<Availability> findAll() {
    return Lists.newArrayList(availabilities.values());
  }

  @Override
  public Optional<Availability> findAvailability(String availability) {
    return Optional.fromNullable(availabilities.get(availability));
  }
}
