package com.clouway.anqp.adapter.http;

/**
 */
class OperatorDTO {
  final Object id;
  final String name;
  final String description;
  final String domainName;
  final String friendlyName;

  OperatorDTO(Object id, String name, String description, String domainName, String friendlyName) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
  }
}
