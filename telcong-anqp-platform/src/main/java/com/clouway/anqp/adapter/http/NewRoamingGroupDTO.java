package com.clouway.anqp.adapter.http;

/**
 */
class NewRoamingGroupDTO {
  final String name;
  final String description;
  final String type;

  NewRoamingGroupDTO(String name, String description, String type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
