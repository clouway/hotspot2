package com.clouway.anqp.adapter.http;

import javax.validation.constraints.Pattern;

/**
 */
class Network3GPPDTO {
  final String name;
  @Pattern(regexp = "^[0-9]{3}",message="Country code must contains exactly 3 digest")
  final String mobileCountryCode;
  @Pattern(regexp = "^[0-9]{2,3}",message="Network code should contains exactly 2~3 digest")
  final String mobileNetworkCode;

  Network3GPPDTO(String name, String mobileCountryCode, String mobileNetworkCode) {
    this.name = name;
    this.mobileCountryCode = mobileCountryCode;
    this.mobileNetworkCode = mobileNetworkCode;
  }
}
