package com.clouway.anqp;


public class AccessPointRequest {
  public final ID id;
  public final String ip;
  public final MacAddress mac;
  public final String serialNumber;
  public final String model;
  public final Venue venue;
  public final GeoLocation geoLocation;
  public final CivicLocation civicLocation;
  public final CapabilityList capabilities;

  public AccessPointRequest(ID id, String ip, MacAddress mac, String serialNumber, String model, Venue venue, GeoLocation geoLocation, CivicLocation civicLocation, CapabilityList capabilities) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilities = capabilities;
  }
}
