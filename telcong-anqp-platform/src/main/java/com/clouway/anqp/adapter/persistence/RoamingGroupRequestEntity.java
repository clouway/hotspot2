package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("rgroups")
class RoamingGroupRequestEntity {
  final Object _id;
  final String name;
  final String description;
  final String type;

  RoamingGroupRequestEntity(Object id, String name, String description, String type) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
