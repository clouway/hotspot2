package com.clouway.telcong.anqp.client.sp;

/**
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
