package com.clouway.telcong.anqp.client.emergency;

/**
 */
public class EmergencyAlertClientFactory {
  private EmergencyAlertClientFactory() {

  }

  public static EmergencyAlertClient create(String host) {
    return new HttpEmergencyAlertClient(host);
  }
}
