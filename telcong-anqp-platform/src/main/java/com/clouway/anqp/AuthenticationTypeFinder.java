package com.clouway.anqp;

import java.util.List;

/**
 * The implementation of this interface will be used to find all the {@link com.clouway.anqp.AuthenticationType}  information
 * when the ASRA(additional steps required for access) flag is required.
 */
public interface AuthenticationTypeFinder {

  /**
   * @return all the available {@link com.clouway.anqp.AuthenticationType}
   */
  List<AuthenticationType> findAll();
}
