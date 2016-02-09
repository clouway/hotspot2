package com.clouway.anqp.adapter.http;

import java.util.List;

/**
 */
final class Errors {
  private final List<ErrorMessage> errorMessages;

  Errors(List<ErrorMessage> errorMessages) {
    this.errorMessages = errorMessages;
  }

  public List<ErrorMessage> getErrorMessages() {
    return errorMessages;
  }
}
