package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.CivicLocation;
import com.clouway.anqp.GeoLocation;
import com.clouway.anqp.api.datastore.Kind;

import java.util.List;

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
  GeoLocationEntity geoLocation;
  CivicLocationEntity civicLocation;
  List<CapabilityEntity> capabilities;

  @SuppressWarnings("unused")
  AccessPointEntity() {
  }

  AccessPointEntity(Object id, Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue, GeoLocationEntity geoLocation, CivicLocationEntity civicLocation, List<CapabilityEntity> capabilities) {
    this._id = id;
    this.operatorId = operatorId;
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