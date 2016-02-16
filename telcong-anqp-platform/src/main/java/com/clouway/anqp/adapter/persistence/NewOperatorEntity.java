package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("operators")
class NewOperatorEntity {
  String name;
  String state;
  String description;
  String domainName;
  String friendlyName;
  String emergencyNumber;
  String ipType;


  NewOperatorEntity(String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipType) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipType = ipType;
  }
}
