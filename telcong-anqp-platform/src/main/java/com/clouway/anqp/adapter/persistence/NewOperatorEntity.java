package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
@Kind("operators")
class NewOperatorEntity {
  final String name;
  final String state;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;
  final String ipType;
  final List<Object> serviceProviderIDs;

  NewOperatorEntity(String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipType) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipType = ipType;
    this.serviceProviderIDs = ImmutableList.copyOf(Lists.newArrayList());
  }
}
