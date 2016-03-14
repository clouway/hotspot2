package com.clouway.telcong.anqp.client.venueitem;

import java.util.List;

/**
 */
public interface VenueItemClient {
  /**
   * Find all venue items
   *
   * @return list of {@link com.clouway.telcong.anqp.client.venueitem.VenueItem}
   */
  List<VenueItem> findAll();
}
