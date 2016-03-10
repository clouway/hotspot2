package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.AuthEntry;
import com.clouway.anqp.EapAuthCatalog;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 */
public class InMemoryEapAuthCatalog implements EapAuthCatalog {
  private final Map<String, AuthEntry> items;

  public InMemoryEapAuthCatalog(AuthEntry... items) {
    Map<String, AuthEntry> itemMap = Maps.newHashMap();

    for (AuthEntry item : items) {
      itemMap.put(item.getInfo().name(), item);
    }

    this.items = ImmutableMap.copyOf(itemMap);
  }

  @Override
  public List<AuthEntry> findAll() {
    return Lists.newArrayList(items.values());
  }

  @Override
  public Optional<AuthEntry> findByInfo(String info) {
    return Optional.fromNullable(items.get(info));
  }
}
