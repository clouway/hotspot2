package com.clouway.anqp.adapter.http;

import com.clouway.anqp.RoamingGroupType;

/**
 */
class RoamingGroupDTO {
  final Object id;
  final String name;
  final String description;
  final RoamingGroupType type;

  RoamingGroupDTO(Object id, String name, String description, RoamingGroupType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
