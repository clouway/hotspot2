package com.clouway.anqp;

import com.clouway.anqp.IPv4.Availability;
import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide
 * <p/>
 * The IPv4 Address Type Availability information provides STA with the information about the availability of
 * IP address version and version that could be allocated to the STA after successful association.
 * <p/>
 * information related to the ip version e.g. corresponding integer code, support checking, etc.
 */
public interface IPv4AvailabilityCatalog {

  /**
   * Returns all the available {@link com.clouway.anqp.IPv4.Availability}
   */
  List<Availability> findAll();

  /**
   * @param name
   * @return true if the name  is supported by the {@link com.clouway.anqp.IPv4.Availability} otherwise false.
   */
  Optional<Availability> findAvailability(String name);
}
