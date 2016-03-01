package com.clouway.anqp;

/**
 * This object is used to represent geospetial location in LCI format
 */
public class GeoLocation {
  public final Double latitude;
  public final Double longitude;

  public GeoLocation(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
