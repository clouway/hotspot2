package com.clouway.anqp.adapter.http;

import com.clouway.anqp.CivicLocation;
import com.clouway.anqp.GeoLocation;

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
  final GeoLocationDTO geoLocation;
  final CivicLocationDTO civicLocation;

  AccessPointDTO(Object id, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO geoLocation, CivicLocationDTO civicLocation) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
  }
}