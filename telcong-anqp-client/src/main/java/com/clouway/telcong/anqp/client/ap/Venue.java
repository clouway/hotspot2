package com.clouway.telcong.anqp.client.ap;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class Venue {
  public final String group;
  public final String type;
  public final List<VenueName> venueNames;

  public Venue(String group, String type, List<VenueName> venueNames) {
    this.group = group;
    this.type = type;
    this.venueNames = ImmutableList.copyOf(venueNames);
  }
}
