package com.clouway.anqp.adapter.http;

/**
 */
class RoamingGroupRequestDTO {
  final Object id;
  final String name;
  final String description;
  final String type;

  RoamingGroupRequestDTO(Object id, String name, String description, String type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
