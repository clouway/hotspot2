package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.RoamingGroupType;
import com.clouway.anqp.api.datastore.Id;
import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("rgroups")
class RoamingGroupEntity {
  @Id
  private Object id;
  private String name;
  private String description;
  private RoamingGroupType type;

  @SuppressWarnings("unused")
  RoamingGroupEntity() {
  }

  RoamingGroupEntity(Object id, String name, String description, RoamingGroupType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }

  Object getId() {
    return id;
  }

  String getName() {
    return name;
  }

  String getDescription() {
    return description;
  }

  RoamingGroupType getType() {
    return type;
  }
}
