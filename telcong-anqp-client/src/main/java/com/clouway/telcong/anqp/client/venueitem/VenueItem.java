package com.clouway.telcong.anqp.client.venueitem;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class VenueItem {
  public final String group;
  public final List<String> types;

  public VenueItem(String group, List<String> types) {
    this.group = group;
    this.types = ImmutableList.copyOf(types);
  }
}
