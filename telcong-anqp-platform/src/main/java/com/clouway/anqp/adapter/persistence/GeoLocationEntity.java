package com.clouway.anqp.adapter.persistence;

/**
 */
class GeoLocationEntity {
  Double latitude;
  Double longitude;

  @SuppressWarnings("unused")
  public GeoLocationEntity() {
  }

  GeoLocationEntity(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
