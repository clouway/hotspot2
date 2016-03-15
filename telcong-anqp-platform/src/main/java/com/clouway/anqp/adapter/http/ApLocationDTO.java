package com.clouway.anqp.adapter.http;

class ApLocationDTO {
  final CivicLocationDTO civicLocation;
  final GeoLocationDTO geoLocation;

  ApLocationDTO(CivicLocationDTO civicLocation, GeoLocationDTO geoLocation) {
    this.civicLocation = civicLocation;
    this.geoLocation = geoLocation;
  }
}
