package com.clouway.anqp.adapter.persistence;

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
  final GeoLocationEntity location;

  NewAccessPointEntity(Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity location) {
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.location = location;
  }
}