package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("operators")
class NewOperatorEntity {
  final String name;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;

  NewOperatorEntity(String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
