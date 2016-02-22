package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("rgroups")
class RoamingGroupRequestEntity {
  Object _id;
  String name;
  String description;
  String type;

  RoamingGroupRequestEntity(Object id, String name, String description, String type) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
