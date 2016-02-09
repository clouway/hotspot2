package com.clouway.anqp.adapter.http;

import java.util.List;

/**
 * Checking the objects against some constraints.
 */
 interface ObjectValidator {
  /**
   * Returns collection of errors. Every error is a message.
   * For every violated property there is an error.
   * <p/>
   * When there are not error, an empty collection is returned.
   *
   * @param object object that should be validated
   * @return Collection of errors.
   *         <p/>
   *         there is corresponding violation message
   */
  List<Error> validate(Object object);
}
