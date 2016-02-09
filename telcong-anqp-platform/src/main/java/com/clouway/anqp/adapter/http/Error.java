package com.clouway.anqp.adapter.http;

/**
 * Error is representing single error.
 */
class Error {
  public final String message;

  /**
   * Creates a new Error by providing path and a message.
   *
   * @param message the error message that causes the violation
   */
  Error(String message) {
    this.message = message;
  }
}