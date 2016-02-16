package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

/**
 */
class OperatorDTO {
  final Object id;
  @NotEmpty(message = "Name can not be empty")
  final String name;
  final String state;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;
  @IpType
  final String ipType;

  OperatorDTO(Object id, String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipType) {
    this.id = id;
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipType = ipType;
  }
}
