package com.clouway.telcong.anqp.client.ap;

/**
 */
public class ApLocation {
  public final CivicLocation civicLocation;
  public final GeoLocation geoLocation;

  public ApLocation(CivicLocation civicLocation, GeoLocation geoLocation) {
    this.civicLocation = civicLocation;
    this.geoLocation = geoLocation;
  }
}
