package com.clouway.anqp.adapter.http;

/**
 */
class NewOperatorDTO {
  final String name;
  final String description;
  final String domainName;
  final String friendlyName;

  NewOperatorDTO(String name, String description, String domainName, String friendlyName) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
  }
}
