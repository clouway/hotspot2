package com.clouway.anqp.adapter.http;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 */
class NewAccessPointDTO {
  final Object operatorId;
  @NotNull
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  @Valid
  final VenueDTO venue;
  final GeoLocationDTO geoLocation;
  final CivicLocationDTO civicLocation;

  public NewAccessPointDTO(Object operatorId, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO geoLocation, CivicLocationDTO civicLocation) {
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
  }
}