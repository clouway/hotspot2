package com.clouway.anqp.adapter.http;

import javax.validation.Valid;

/**
 */
class AccessPointDTO {
  final Object id;
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  @Valid
  final VenueDTO venue;
  final GeoLocationDTO location;

  public AccessPointDTO(Object id, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO location) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.location = location;
  }
}