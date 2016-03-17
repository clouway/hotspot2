package com.clouway.telcong.anqp.client.rg;

/**
 */
public class RoamingGroupClientFactory {
  private RoamingGroupClientFactory() {
  }

  public static RoamingGroupClient create(String host) {
    return new HttpRoamingGroupClient(host);
  }
}

