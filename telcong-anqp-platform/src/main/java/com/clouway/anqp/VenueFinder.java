package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * The main responsibility of this interface is to find all the {@link com.clouway.anqp.VenueType} per {@link com.clouway.anqp.VenueGroup}
 */
public interface VenueFinder {
  /**
   * @return the available {@link VenueItem}
   */
  List<VenueItem> findAll();

  /**
   * Finds {@link com.clouway.anqp.VenueType} per {@link com.clouway.anqp.VenueGroup}
   *
   * @param group {@link com.clouway.anqp.VenueGroup}
   * @return  {@link com.clouway.anqp.VenueTypeList}
   */
  Optional<VenueTypeList> findTypesBy(VenueGroup group);
}