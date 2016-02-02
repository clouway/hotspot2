package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Id;
import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("aps")
class AccessPointEntity {
  @Id
  private Object id;
  private String ip;
  private String mac;
  private String serialNumber;
  private String model;

  AccessPointEntity() {
  }

  AccessPointEntity(Object id, String ip, String mac, String serialNumber, String model) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }

  public Object getId() {
    return id;
  }

  public String getIp() {
    return ip;
  }

  public String getMac() {
    return mac;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public String getModel() {
    return model;
  }
}