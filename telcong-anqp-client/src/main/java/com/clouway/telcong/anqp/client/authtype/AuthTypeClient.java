package com.clouway.telcong.anqp.client.authtype;

import java.util.List;

/**
 */
public interface AuthTypeClient {

  /**
   * Find all AuthenticationType
   *
   * @return list of {@link com.clouway.telcong.anqp.client.authtype.AuthenticationType}
   */
  List<AuthenticationType> findAll();
}
