package com.clouway.anqp;

/**
 * This class contains cellular information such as network advertisement
 * information e.g., network codes and country codes to assist a 3GPP non-AP STA in selecting an AP to
 * access 3GPP networks.
 */
public class Network3GPP {
  public final String name;
  public final String mobileCountryCode;
  public final String mobileNetworkCode;

  public Network3GPP(String name, String mobileCountryCode, String mobileNetworkCode) {
    this.name = name;
    this.mobileCountryCode = mobileCountryCode;
    this.mobileNetworkCode = mobileNetworkCode;
  }
}
