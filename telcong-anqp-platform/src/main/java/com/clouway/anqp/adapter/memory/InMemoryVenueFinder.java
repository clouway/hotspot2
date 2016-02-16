package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueItem;
import com.clouway.anqp.VenueTypeList;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 */
class InMemoryVenueFinder implements VenueFinder {
  private final Map<VenueGroup, VenueItem> items;

  InMemoryVenueFinder(VenueItem... items) {
    Map<VenueGroup, VenueItem> temp = Maps.newHashMap();

    for (VenueItem item : items) {
      temp.put(item.group, item);
    }

    this.items = ImmutableMap.copyOf(temp);
  }

  @Override
  public List<VenueItem> findAll() {
    return ImmutableList.copyOf(items.values());
  }

  @Override
  public Optional<VenueTypeList> findTypesBy(VenueGroup group) {
    if (!items.containsKey(group)) {
      return Optional.absent();
    }

    return Optional.of(items.get(group).types);
  }
}