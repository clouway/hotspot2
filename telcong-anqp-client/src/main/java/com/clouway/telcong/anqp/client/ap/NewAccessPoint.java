package com.clouway.telcong.anqp.client.ap;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class NewAccessPoint {
  public final Object operatorId;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;
  public final Venue venue;
  public final GeoLocation geoLocation;
  public final CivicLocation civicLocation;
  public final List<Integer> capabilityIds;

  public NewAccessPoint(Object operatorId, String ip, String mac, String serialNumber, String model, Venue venue, GeoLocation geoLocation, CivicLocation civicLocation, List<Integer> capabilityIds) {
    this.operatorId = operatorId;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilityIds = ImmutableList.copyOf(capabilityIds);
  }
}
