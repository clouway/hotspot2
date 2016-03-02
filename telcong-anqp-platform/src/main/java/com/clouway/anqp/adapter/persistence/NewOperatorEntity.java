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
  final List<Object> serviceProviderIDs;
  final String ipV4;
  final String ipV6;

  NewOperatorEntity(String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipV4, String ipV6) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.serviceProviderIDs = ImmutableList.copyOf(Lists.newArrayList());
    this.ipV4 = ipV4;
    this.ipV6 = ipV6;
  }
}
