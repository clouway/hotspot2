package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide information related
 * to supported capabilities
 */
public interface CapabilityCatalog {

  /***
   * Return all available capabilities
   *
   * @return {@link CapabilityList} with all available capabilities
   */
  CapabilityList findAll();

  /**
   * Finds {@link Capability} for given integer representing capability's infoId
   *
   * @param id - infoId of the capability
   * @return corresponded {@link Capability}
   */
  Optional<Capability> findById(Integer id);

  /**
   * Returns {@link CapabilityList} with capabilities ordered by id for the provided list of ids
   *
   * @param ids - list with ids if the capabilities
   * @return all found capabilities
   */
  CapabilityList findByIds(List<Integer> ids);

  /**
   * Checks whether capability with given infoId is supported or not
   *
   * @param id of the capability
   * @return true if is supported, otherwise false
   */
  boolean isSupported(Integer id);
}
