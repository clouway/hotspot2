package com.clouway.telcong.anqp.client.operator;

/**
 */
public class OperatorClientFactory {
  private OperatorClientFactory() {
  }

  public static OperatorClient create(String host) {
    return new HttpOperatorClient(host);
  }
}
