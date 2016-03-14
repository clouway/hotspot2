package com.clouway.telcong.anqp.client.venueitem;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class VenueItemBuilder {
  private String group = "group";
  private List<String> types = Lists.newArrayList("type");

  public static VenueItemBuilder newVenueItem() {
    return new VenueItemBuilder();
  }

  public VenueItemBuilder group(String group) {
    this.group = group;
    return this;
  }

  public VenueItemBuilder types(String... types) {
    this.types = Lists.newArrayList(types);
    return this;
  }

  public VenueItem build() {
    return new VenueItem(group, types);
  }

}
