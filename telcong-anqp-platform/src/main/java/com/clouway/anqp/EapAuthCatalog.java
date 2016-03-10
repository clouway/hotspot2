package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide all
 * the EAP authentication description for a given authentication info.
 */
public interface EapAuthCatalog {
  /**
   * @return all the {@link AuthEntry}
   */
  List<AuthEntry> findAll();

  /**
   * @param info - string representation of {@link Auth.Info}
   * @return  {@link com.clouway.anqp.AuthEntry}
   */
  Optional<AuthEntry> findByInfo(String info);
}
