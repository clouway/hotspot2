package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.CivicLocation;
import com.clouway.anqp.GeoLocation;
import com.clouway.anqp.api.datastore.Kind;

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

  NewAccessPointEntity(Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity geoLocation, CivicLocationEntity civicLocation) {
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