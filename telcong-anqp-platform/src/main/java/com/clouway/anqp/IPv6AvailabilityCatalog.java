package com.clouway.anqp;

import com.clouway.anqp.IPv6.Availability;
import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide
 * <p/>
 * The IPv6 Address Type Availability information provides STA with the information about the availability of
 * IP address version and version that could be allocated to the STA after successful association.
 * <p/>
 * information related to the ip version e.g. corresponding integer code, support checking, etc.
 */
public interface IPv6AvailabilityCatalog {
  /**
   * Returns all the available {@link com.clouway.anqp.IPv6.Availability}
   */
  List<Availability> findAll();

  /**
   * @param name
   * @return true if the availability  is supported by the {@link com.clouway.anqp.IPv6.Availability} otherwise false.
   */
  Optional<Availability> findByAvailability(String name);
}
