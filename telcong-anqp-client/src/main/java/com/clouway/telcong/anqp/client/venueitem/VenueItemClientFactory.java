package com.clouway.telcong.anqp.client.venueitem;

/**
 */
public class VenueItemClientFactory {
  private VenueItemClientFactory() {
  }

  public static VenueItemClient create(String host) {
    return new HttpVenueItemClient(host);
  }
}
