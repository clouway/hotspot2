package com.clouway.anqp.adapter.http.client;

/**
 *
 */
public final class Response {
  private final int statusCode;
  private final String content;

  protected Response(int statusCode, String content) {
    this.statusCode = statusCode;
    this.content = content;
  }

  public int code() {
    return statusCode;
  }

  public String content() {
    return content;
  }
}
