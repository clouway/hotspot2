package com.clouway.telcong.anqp.client.nai;

/**
 */
public class NaiRealmClientFactory {
  private NaiRealmClientFactory() {
  }

  public static NaiRealmClient create(String host) {
    return new HttpNaiRealmClient(host);
  }
}
