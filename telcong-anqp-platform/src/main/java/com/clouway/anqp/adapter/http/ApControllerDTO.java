package com.clouway.anqp.adapter.http;

/**
 */
class ApControllerDTO {
  public final Object id;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;

  public ApControllerDTO(Object id, String ip, String mac, String serialNumber, String model) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}