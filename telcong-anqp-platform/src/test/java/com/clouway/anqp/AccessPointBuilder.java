package com.clouway.anqp;

import com.google.common.collect.Lists;

public class AccessPointBuilder {
  private ID id = new ID(1);
  private String ip = "ip";
  private MacAddress mac = new MacAddress("mac");
  private String serialNumber = "sn";
  private String model = "model";
  private Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("name", new Language("language"))));
  private GeoLocation geoLocation = new GeoLocation(10.0, 20.0);
  private CivicLocation civicLocation = new CivicLocation("country", "city", "street", "number", "postCode");
  private CapabilityList capabilities = new CapabilityList(Lists.newArrayList(new Capability(1, "capability")));

  private AccessPointBuilder() {
  }

  public static AccessPointBuilder newAp() {
    return new AccessPointBuilder();
  }

  public AccessPointBuilder id(ID id) {
    this.id = id;
    return this;
  }

  public AccessPointBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public AccessPointBuilder mac(MacAddress mac) {
    this.mac = mac;
    return this;
  }

  public AccessPointBuilder serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  public AccessPointBuilder model(String model) {
    this.model = model;
    return this;
  }

  public AccessPointBuilder venue(Venue venue) {
    this.venue = venue;
    return this;
  }

  public AccessPointBuilder geoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
    return this;
  }

  public AccessPointBuilder civicLocation(CivicLocation civicLocation) {
    this.civicLocation = civicLocation;
    return this;
  }

  public AccessPointBuilder capabilities(CapabilityList capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  public AccessPoint build() {
    return new AccessPoint(id, ip, mac, serialNumber, model, venue, geoLocation, civicLocation, capabilities);
  }
}
