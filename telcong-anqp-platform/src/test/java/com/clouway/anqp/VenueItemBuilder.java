package com.clouway.anqp;

/**
 */
public class VenueItemBuilder {
  private VenueGroup group = new VenueGroup("Undefined");
  private VenueTypeList types = new VenueTypeList();

  public static VenueItemBuilder newVenueItemBuilder() {
    return new VenueItemBuilder();
  }

  public VenueItemBuilder group(String group) {
    this.group = new VenueGroup(group);
    return this;
  }

  public VenueItemBuilder types(VenueType... types) {
    this.types = new VenueTypeList(types);
    return this;
  }

  public VenueItem build() {
    return new VenueItem(group, types);
  }
}
