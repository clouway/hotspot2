package com.clouway.telcong.anqp.client.ap;

/**
 */
public class ApLocationBuilder {
  private CivicLocation civicLocation = new CivicLocation("country", "city", "street", "sn", "postCode");
  private GeoLocation geoLocation = new GeoLocation(33.3333, 44.4444);

  public static ApLocationBuilder newLocation() {
    return new ApLocationBuilder();
  }

  public ApLocationBuilder civicLocation(CivicLocation civicLocation) {
    this.civicLocation = civicLocation;
    return this;
  }

  public ApLocationBuilder geoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
    return this;
  }

  public ApLocation build() {
    return new ApLocation(civicLocation, geoLocation);
  }
}
