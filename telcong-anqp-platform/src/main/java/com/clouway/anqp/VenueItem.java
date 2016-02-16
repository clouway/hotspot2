package com.clouway.anqp;

/**
 * The Venue Type field is a 2-octet field. It contains Venue Group and Venue Type subfields.
 */
public class VenueItem {
  public final VenueGroup group;
  public final VenueTypeList types;

  public VenueItem(VenueGroup group, VenueTypeList types) {
    this.group = group;
    this.types = types;
  }
}