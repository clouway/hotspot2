package com.clouway.telcong.anqp.client.capability;

import java.util.List;

/**
 */
public interface CapabilityClient {

  /**
   * Retrieve all anqp capabilities
   *
   * @return list with {@link com.clouway.telcong.anqp.client.capability.Capability}
   */
  List<Capability> findAll();
}
