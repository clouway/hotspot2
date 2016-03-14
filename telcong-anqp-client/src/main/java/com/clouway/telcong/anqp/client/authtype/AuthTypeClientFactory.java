package com.clouway.telcong.anqp.client.authtype;

/**
 */
public class AuthTypeClientFactory {
  private AuthTypeClientFactory() {
  }

  public static AuthTypeClient create(String host) {
    return new HttpAuthTypeClient(host);
  }
}
