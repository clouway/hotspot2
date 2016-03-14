package com.clouway.telcong.anqp.client.capability;

/**
 */
public class CapabilityClientFactory {
  private CapabilityClientFactory() {
  }

  public static CapabilityClient create(String host) {
    return new HttpCapabilityClient(host);
  }
}
