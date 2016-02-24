package com.clouway.anqp;

/**
 */
public class AccessPoint {
  public final ID id;
  public final String ip;
  public final MacAddress mac;
  public final String serialNumber;
  public final String model;
  public final Venue venue;

  public AccessPoint(ID id, String ip, MacAddress mac, String serialNumber, String model, Venue venue) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}