package com.clouway.anqp;

import com.clouway.anqp.EAP.Method;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide all
 * the EAP authentication method supported by the hotspot realm.
 */
public interface EapMethodCatalog {

  /**
   * @return all the available {@link com.clouway.anqp.EAP.Method}
   */
  List<Method> findAll();
}
