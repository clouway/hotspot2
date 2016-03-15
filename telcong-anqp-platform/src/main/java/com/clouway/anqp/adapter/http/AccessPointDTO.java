package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import javax.validation.Valid;
import java.util.List;

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
  final List<CapabilityDTO> capabilities;

  AccessPointDTO(Object id, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO geoLocation, CivicLocationDTO civicLocation, List<CapabilityDTO> capabilities) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilities = ImmutableList.copyOf(capabilities);
  }
}