package com.clouway.telcong.anqp.client.ap;

/**
 */
public class AccessPointClientFactory {
  private AccessPointClientFactory() {

  }

  public static AccessPointClient create(String host) {
    return new HttpAccessPointClient(host);
  }

}
