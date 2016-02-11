package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.RoamingGroupType;
import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("rgroups")
class NewRoamingGroupEntity {
  String name;
  String description;
  RoamingGroupType type;

  NewRoamingGroupEntity(String name, String description, RoamingGroupType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
