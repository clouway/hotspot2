package com.clouway.anqp.adapter.http;

import org.hibernate.validator.constraints.NotEmpty;

/**
 */
class OperatorDTO {
  final Object id;
  @NotEmpty(message = "Name can not be empty")
  final String name;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;

  OperatorDTO(Object id, String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
