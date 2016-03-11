package com.clouway.telcong.anqp.client.apcontroller;

/**
 */
public final class ApControllerClientFactory {
  /**
   * Construction need to be disabled.
   */
  private ApControllerClientFactory() {
  }

  public static ApControllerClient create(String host) {
    return new HttpApControllerClient(host);
  }
}
