package com.clouway.anqp.adapter.http;

/**
 */
class NewOperatorDTO {
  final String name;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;

  NewOperatorDTO(String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
