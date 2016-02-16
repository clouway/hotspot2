package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("aps")
class NewAccessPointEntity {
  public final Object operatorId;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;
  public final VenueEntity venue;

  public NewAccessPointEntity(Object operatorId, String ip, String mac, String serialNumber, String model, VenueEntity venue) {
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}