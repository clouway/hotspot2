package com.clouway.anqp;

import java.util.List;

/**
 * Implementation of this interface will be responsible to provide the Encoding Type subfields.
 */
public interface EncodingCatalog {

  /**
   * @return a list of all the encoding types
   */
  List<Encoding> findAll();
}
