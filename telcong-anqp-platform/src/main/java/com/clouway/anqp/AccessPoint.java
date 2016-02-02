package com.clouway.anqp;

/**
 */
public class AccessPoint {
  public final Object id;
  public final String ip;
  public final MacAddress mac;
  public final String serialNumber;
  public final String model;

  public AccessPoint(Object id, String ip, MacAddress mac, String serialNumber, String model) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}