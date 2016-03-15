package com.clouway.telcong.anqp.client.ap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class AccessPointRequestBuilder {
  private Object id = "id";
  private String ip = "ip";
  private String mac = "mac";
  private String serialNumber = "sn";
  private String model = "model";
  private Venue venue = new Venue("group", "type", Lists.newArrayList(new VenueName("name", "language")));
  private GeoLocation geoLocation = new GeoLocation(33.3333, 44.4444);
  private CivicLocation civicLocation = new CivicLocation("country", "city", "street", "number", "postCode");
  private List<Integer> capabilityIds = Lists.newArrayList(1);

  public static AccessPointRequestBuilder newAPRequest() {
    return new AccessPointRequestBuilder();
  }

  public AccessPointRequestBuilder id(Object id) {
    this.id = id;
    return this;
  }

  public AccessPointRequestBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public AccessPointRequestBuilder mac(String mac) {
    this.mac = mac;
    return this;
  }

  public AccessPointRequestBuilder serialNumber(String sn) {
    this.serialNumber = sn;
    return this;
  }

  public AccessPointRequestBuilder model(String model) {
    this.model = model;
    return this;
  }

  public AccessPointRequestBuilder venue(Venue venue) {
    this.venue = venue;
    return this;
  }

  public AccessPointRequestBuilder geoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
    return this;
  }

  public AccessPointRequestBuilder civicLocation(CivicLocation civicLocation) {
    this.civicLocation = civicLocation;
    return this;
  }
  public AccessPointRequestBuilder capabilityIds(Integer... capabilityIds) {
    this.capabilityIds = Lists.newArrayList(capabilityIds);
    return this;
  }

  public AccessPointRequest build() {
    return new AccessPointRequest(id, ip, mac, serialNumber, model, venue, geoLocation, civicLocation, capabilityIds);
  }
}
