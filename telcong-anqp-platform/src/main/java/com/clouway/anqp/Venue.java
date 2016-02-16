package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * The main purpose of the Venue.class is to define the venue group, venue type and information to be sent in an
 * Access network Query Protocol (ANQP) information element in
 * a Generic Advertisement Service (GAS) query response.
 * If a client uses the Generic Advertisement Service (GAS) to post an ANQP query to an Access Point,
 * the AP will return ANQP Information Elements with the values configured in this profile.
 */
public class Venue {
  public final VenueGroup group;
  public final VenueType type;
  public final List<VenueName> names;

  public Venue(VenueGroup group, VenueType type, List<VenueName> names) {
    this.group = group;
    this.type = type;
    this.names = ImmutableList.copyOf(names);
  }
}