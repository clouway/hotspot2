package com.clouway.anqp;

/**
 */
public class NewApController {
  public final String ip;
  public final MacAddress mac;
  public final String serialNumber;
  public final String model;

  public NewApController(String ip, MacAddress mac, String serialNumber, String model) {
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}