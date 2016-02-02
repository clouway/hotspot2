package com.clouway.anqp.adapter.http;

/**
 */
class NewAccessPointDTO {
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;

  public NewAccessPointDTO(String ip, String mac, String serialNumber, String model) {
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
  }
}