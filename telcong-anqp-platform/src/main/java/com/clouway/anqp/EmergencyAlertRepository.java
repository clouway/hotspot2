package com.clouway.anqp;


import java.util.List;

/**
 * Implementations of this interface will be responsible for operations related to CAP Alert Messages
 */
public interface EmergencyAlertRepository {
  /**
   * Provide all found {@link CapMessage}s
   *
   * @return all available CAP Alert Message
   */
  List<CapMessage> findAll();
}
