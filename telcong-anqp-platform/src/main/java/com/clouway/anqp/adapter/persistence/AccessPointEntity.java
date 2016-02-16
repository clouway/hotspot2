package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("aps")
class AccessPointEntity {
  Object _id;
  String ip;
  String mac;
  String serialNumber;
  String model;
  VenueEntity venue;

  AccessPointEntity() {
  }

  AccessPointEntity(Object id, String ip, String mac, String serialNumber, String model, VenueEntity venue) {
    this._id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}