package com.clouway.anqp.adapter.http;

import com.clouway.anqp.RoamingGroupType;

/**
 */
class NewRoamingGroupDTO {
  final String name;
  final String description;
  final RoamingGroupType type;

  NewRoamingGroupDTO(String name, String description, RoamingGroupType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
