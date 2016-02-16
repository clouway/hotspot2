package com.clouway.anqp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

/**
 */
public class VenueTypeList {
  private final Set<VenueType> venues;

  public VenueTypeList(VenueType... venues) {
    this.venues = ImmutableSet.copyOf(venues);
  }

  public List<VenueType> values() {
    return ImmutableList.copyOf(venues);
  }

  public Boolean contains(VenueType type) {
    return venues.contains(type);
  }
}