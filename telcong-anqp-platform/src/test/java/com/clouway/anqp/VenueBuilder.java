package com.clouway.anqp;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class VenueBuilder {
  private VenueGroup group = new VenueGroup("Undefined");
  private VenueType type = new VenueType("Undefined");
  private List<VenueName> names = Lists.newArrayList();

  public static VenueBuilder newVenueBuilder() {
    return new VenueBuilder();
  }

  public VenueBuilder group(String group) {
    this.group = new VenueGroup(group);
    return this;
  }

  public VenueBuilder type(String type) {
    this.type = new VenueType(type);
    return this;
  }

  public VenueBuilder names(VenueName... names) {
    this.names = Lists.newArrayList(names);
    return this;
  }

  public Venue build() {
    return new Venue(group, type, names);
  }
}

