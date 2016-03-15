package com.clouway.telcong.anqp.client.serviceprovider;

/**
 */
public class ServiceProviderClientFactory {
  private ServiceProviderClientFactory() {
  }

  public static ServiceProviderClient create(String host) {
    return new HttpServiceProviderClient(host);
  }
}
