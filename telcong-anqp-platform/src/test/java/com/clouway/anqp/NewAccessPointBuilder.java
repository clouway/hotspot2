package com.clouway.anqp;

import com.google.common.collect.Lists;

public class NewAccessPointBuilder {
  private ID operatorId = new ID("operID");
  private String ip = "ip";
  private MacAddress mac = new MacAddress("aa:bb:cc");
  private String serialNumber = "sn";
  private String model = "model";
  private Venue venue =  new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));

  public static NewAccessPointBuilder newAP() {
    return new NewAccessPointBuilder();
  }

  public NewAccessPointBuilder operatorId(ID operatorId) {
    this.operatorId = operatorId;
    return this;
  }

  public NewAccessPointBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public NewAccessPointBuilder mac(MacAddress mac) {
    this.mac = mac;
    return this;
  }

  public NewAccessPointBuilder sn(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  public NewAccessPointBuilder model(String model) {
    this.model = model;
    return this;
  }

  public NewAccessPointBuilder venue(Venue venue) {
    this.venue = venue;
    return this;
  }

  public NewAccessPoint build() {
    return new NewAccessPoint(operatorId, ip, mac, serialNumber, model, venue);
  }
}