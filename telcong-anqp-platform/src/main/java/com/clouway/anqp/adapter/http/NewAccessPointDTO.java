package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;
import org.apache.bval.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
  @Capability(message = "Unsuccessful AP creation. Found capability id that is not supported")
  final List<Integer> capabilityIds;

  public NewAccessPointDTO(Object operatorId, String ip, String mac, String serialNumber, String model, VenueDTO venue, GeoLocationDTO geoLocation, CivicLocationDTO civicLocation, List<Integer> capabilityIds) {
    this.operatorId = operatorId;
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