package com.clouway.anqp.adapter.http;

/**
 */
final class ErrorMessage {
  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}