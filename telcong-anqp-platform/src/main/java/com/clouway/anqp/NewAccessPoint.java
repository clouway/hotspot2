package com.clouway.anqp;

/**
 */
public class NewAccessPoint {
  public final ID operatorId;
  public final String ip;
  public final MacAddress mac;
  public final String serialNumber;
  public final String model;
  public final Venue venue;

  public NewAccessPoint(ID operatorId, String ip, MacAddress mac, String serialNumber, String model, Venue venue) {
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}