package com.clouway.anqp.adapter.http;

/**
 */
class GeoLocationDTO {
  final Double latitude;
  final Double longitude;

  GeoLocationDTO(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
