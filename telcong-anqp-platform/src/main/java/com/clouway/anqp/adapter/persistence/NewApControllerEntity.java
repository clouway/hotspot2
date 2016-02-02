package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("controllers")
class NewApControllerEntity {
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;

  public NewApControllerEntity(String ip, String mac, String serialNumber, String model) {
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}