package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.RoamingGroupType;
import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
@Kind("rgroups")
class NewRoamingGroupEntity {
  String name;
  String description;
  RoamingGroupType type;
  List<Object> operatorIDs;

  NewRoamingGroupEntity(String name, String description, RoamingGroupType type) {
    this.name = name;
    this.description = description;
    this.type = type;
    this.operatorIDs = ImmutableList.copyOf(Lists.newArrayList());
  }
}
