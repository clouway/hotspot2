package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.CivicLocation;
import com.clouway.anqp.GeoLocation;
import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
@Kind("aps")
class NewAccessPointEntity {
  final Object operatorId;
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  final VenueEntity venue;
  final GeoLocationEntity geoLocation;
  final CivicLocationEntity civicLocation;
  final List<CapabilityEntity> capabilities;

  NewAccessPointEntity(Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity geoLocation, CivicLocationEntity civicLocation, List<CapabilityEntity> capabilities) {
    this.operatorId = operatorId;
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