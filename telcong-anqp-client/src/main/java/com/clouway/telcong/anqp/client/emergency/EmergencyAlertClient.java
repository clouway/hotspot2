package com.clouway.telcong.anqp.client.emergency;

import java.util.List;

/**
 */
public interface EmergencyAlertClient {
  /**
   * Retrieve all CAP messages
   *
   * @return list of {@link com.clouway.telcong.anqp.client.emergency.CapMessage}
   */
  List<CapMessage> findAll();
}
