package com.clouway.anqp.adapter.persistence;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class VenueEntity {
  String group;
  String type;
  List<VenueNameEntity> venueNames;

  public VenueEntity() {
  }

  public VenueEntity(String group, String type, List<VenueNameEntity> venueNames) {
    this.group = group;
    this.type = type;
    this.venueNames = ImmutableList.copyOf(venueNames);
  }
}
