package com.clouway.telcong.anqp.client.apcontroller;

/**
 */
public class ApController {
  public final Object id;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;

  public ApController(Object id, String ip, String mac, String serialNumber, String model) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}
