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

  AccessPointRequestEntity(Object id, String ip, String mac, String serialNumber, String model, VenueEntity venue) {
    this._id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}
