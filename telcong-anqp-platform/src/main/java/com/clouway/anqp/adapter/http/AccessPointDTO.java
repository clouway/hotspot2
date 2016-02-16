package com.clouway.anqp.adapter.http;

import javax.validation.Valid;

/**
 */
class AccessPointDTO {
  public final Object id;
  public final String ip;
  public final String mac;
  public final String serialNumber;
  public final String model;

  @Valid
  public final VenueDTO venue;

  public AccessPointDTO(Object id, String ip, String mac, String serialNumber, String model, VenueDTO venue) {
    this.id = id;
    this.ip = ip;
    this.mac = mac;
    this.serialNumber = serialNumber;
    this.model = model;
    this.venue = venue;
  }
}