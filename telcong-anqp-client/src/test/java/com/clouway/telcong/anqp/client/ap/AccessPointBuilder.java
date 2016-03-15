package com.clouway.telcong.anqp.client.ap;

import com.clouway.telcong.anqp.client.capability.Capability;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class AccessPointBuilder {
  private Object id = "id";
  private String ip = "ip";
  private String mac = "mac";
  private String serialNumber = "sn";
  private String model = "model";
  private Venue venue = new Venue("group", "type", Lists.newArrayList(new VenueName("name", "language")));
  private GeoLocation geoLocation = new GeoLocation(33.3333, 44.4444);
  private CivicLocation civicLocation = new CivicLocation("country", "city", "street", "number", "postCode");
  private List<Capability> capabilities = Lists.newArrayList(new Capability(1, "name"));

  public static AccessPointBuilder accessPoint() {
    return new AccessPointBuilder();
  }

  public AccessPointBuilder id(Object id) {
    this.id = id;
    return this;
  }

  public AccessPointBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public AccessPointBuilder mac(String mac) {
    this.mac = mac;
    return this;
  }

  public AccessPointBuilder serialNumber(String sn) {
    this.serialNumber = sn;
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
  public AccessPointBuilder capabilities(Capability... capabilities) {
    this.capabilities = Lists.newArrayList(capabilities);
    return this;
  }

  public AccessPoint build() {
    return new AccessPoint(id, ip, mac, serialNumber, model, venue, geoLocation, civicLocation, capabilities);
  }
}
