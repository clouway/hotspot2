package com.clouway.telcong.anqp.client.ap;

import com.clouway.telcong.anqp.client.capability.Capability;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class AccessPoint {
  public final Object id;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;
  public final Venue venue;
  public final GeoLocation geoLocation;
  public final CivicLocation civicLocation;
  public final List<Capability> capabilities;

  public AccessPoint(Object id, String ip, String mac, String serialNumber, String model, Venue venue, GeoLocation geoLocation, CivicLocation civicLocation, List<Capability> capabilities) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
    this.geoLocation = geoLocation;
    this.civicLocation = civicLocation;
    this.capabilities = ImmutableList.copyOf(capabilities);
  }
}
