package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide
 * information related to the ip type e.g. corresponding integer code, support checking, etc.
 */
public interface IpTypeCatalog {
  /**
   * Return all available {@link IpType} names
   *
   * @return list with all available ip type names
   */
  List<String> getAll();

  /**
   * Find corresponding Integer value for given type
   *
   * @param type - one of the operator's supported ip address types
   * @return integer corresponding to the type
   */
  Optional<Integer> findId(String type);

  /**
   * Check whether given string representation of {@link IpType} is supported or not
   *
   * @param type - type value that will be checked
   * @return true for supported type or false for unsupported type
   */
  boolean isSupported(String type);
}
