package com.clouway.telcong.anqp.client.ap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class NewAccessPointBuilder {
  private Object operatorId = "id";
  private String ip = "ip";
  private String mac = "mac";
  private String serialNumber = "sn";
  private String model = "model";
  private Venue venue = new Venue("group", "type", Lists.newArrayList(new VenueName("name", "language")));
  private GeoLocation geoLocation = new GeoLocation(33.3333, 44.4444);
  private CivicLocation civicLocation = new CivicLocation("country", "city", "street", "number", "postCode");
  private List<Integer> capabilityIds = Lists.newArrayList(256);

  public static NewAccessPointBuilder newAP() {
    return new NewAccessPointBuilder();
  }

  public NewAccessPointBuilder operatorId(Object operatorId) {
    this.operatorId = operatorId;
    return this;
  }

  public NewAccessPointBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public NewAccessPointBuilder mac(String mac) {
    this.mac = mac;
    return this;
  }

  public NewAccessPointBuilder serialNumber(String sn) {
    this.serialNumber = sn;
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

  public NewAccessPointBuilder geoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
    return this;
  }

  public NewAccessPointBuilder civicLocation(CivicLocation civicLocation) {
    this.civicLocation = civicLocation;
    return this;
  }
  public NewAccessPointBuilder capabilityIds(Integer... capabilityIds) {
    this.capabilityIds = Lists.newArrayList(capabilityIds);
    return this;
  }

  public NewAccessPoint build() {
    return new NewAccessPoint(operatorId, ip, mac, serialNumber, model, venue, geoLocation, civicLocation, capabilityIds);
  }
}
