package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

import java.util.List;

@Kind("aps")
class AccessPointRequestEntity {
  final Object _id;
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  final VenueEntity venue;
  final GeoLocationEntity geoLocation;
  final CivicLocationEntity civicLocation;
  final List<CapabilityEntity> capabilities;

  AccessPointRequestEntity(Object id, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity geoLocation, CivicLocationEntity civicLocation, List<CapabilityEntity> capabilities) {
    this._id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilities = capabilities;
  }
}
