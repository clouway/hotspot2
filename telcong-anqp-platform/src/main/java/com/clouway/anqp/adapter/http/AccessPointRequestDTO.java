package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import javax.validation.Valid;
import java.util.List;

class AccessPointRequestDTO {
  final Object id;
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  @Valid
  final VenueDTO venue;
  final GeoLocationDTO geoLocation;
  final CivicLocationDTO civicLocation;
  @Capability(message = "Unsuccessful AP edition. Found capability id that is not supported")
  final List<Integer> capabilityIds;

  AccessPointRequestDTO(Object id, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO geoLocation, CivicLocationDTO civicLocation, List<Integer> capabilityIds) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilityIds = ImmutableList.copyOf(capabilityIds);
  }
}
