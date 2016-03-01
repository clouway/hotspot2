package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("aps")
class AccessPointRequestEntity {
  final Object _id;
  final String ip;
  final String mac;
  final String serialNumber;
  final String model;
  final VenueEntity venue;
  final GeoLocationEntity location;

  AccessPointRequestEntity(Object id, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity location) {
    this._id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.location = location;
  }
}
