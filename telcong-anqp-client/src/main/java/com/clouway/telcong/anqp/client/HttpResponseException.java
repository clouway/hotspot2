package com.clouway.telcong.anqp.client;

/**
 */
public class HttpResponseException extends RuntimeException {
  public final Integer code;
  public final String body;

  public HttpResponseException(Integer code, String body) {
    this.code = code;
    this.body = body;
  }
}
