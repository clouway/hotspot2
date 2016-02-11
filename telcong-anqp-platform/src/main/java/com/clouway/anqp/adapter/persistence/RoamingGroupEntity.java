package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.RoamingGroupType;
import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("rgroups")
class RoamingGroupEntity {
  Object _id;
  String name;
  String description;
  RoamingGroupType type;

  @SuppressWarnings("unused")
  RoamingGroupEntity() {
  }

  RoamingGroupEntity(Object id, String name, String description, RoamingGroupType type) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
