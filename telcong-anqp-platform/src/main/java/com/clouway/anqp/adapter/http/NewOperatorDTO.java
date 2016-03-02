package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

/**
 */
class NewOperatorDTO {
  @NotEmpty(message = "Name can not be empty")
  final String name;
  final String state;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;
  @ValidIPv4
  final String ipV4;
  @ValidIPv6
  final String ipV6;

  NewOperatorDTO(String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipV4, String ipV6) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipV4 = ipV4;
    this.ipV6 = ipV6;
  }
}
