package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("aps")
class AccessPointEntity {
  Object _id;
  Object operatorId;
  String ip;
  String mac;
  String serialNumber;
  String model;
  VenueEntity venue;
  GeoLocationEntity location;

  AccessPointEntity() {
  }

  AccessPointEntity(Object id, Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity location) {
    this._id = id;
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.location = location;
  }
}