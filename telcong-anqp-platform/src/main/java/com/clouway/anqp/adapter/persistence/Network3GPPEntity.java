package com.clouway.anqp.adapter.persistence;

/**
 */
class Network3GPPEntity {
  String name;
  String mobileCountryCode;
  String mobileNetworkCode;

  @SuppressWarnings("unused")
  public Network3GPPEntity() {

  }

  Network3GPPEntity(String name, String mobileCountryCode, String mobileNetworkCode) {
    this.name = name;
    this.mobileCountryCode = mobileCountryCode;
    this.mobileNetworkCode = mobileNetworkCode;
  }
}
