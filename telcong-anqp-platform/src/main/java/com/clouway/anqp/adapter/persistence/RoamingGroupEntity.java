package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;

/**
 */
@Kind("rgroups")
class RoamingGroupEntity {
  Object _id;
  String name;
  String description;
  String type;
  List<Object> operatorIDs;

  @SuppressWarnings("unused")
  RoamingGroupEntity() {
  }

  RoamingGroupEntity(Object id, String name, String description, String type, List<Object> operatorIDs) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.operatorIDs = ImmutableList.copyOf(operatorIDs);
  }
}
