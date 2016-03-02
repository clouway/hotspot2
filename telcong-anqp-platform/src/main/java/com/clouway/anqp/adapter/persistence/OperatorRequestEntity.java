package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("operators")
class OperatorRequestEntity {
  final Object _id;
  final String name;
  final String state;
  final String description;
  final String domainName;
  final String friendlyName;
  final String emergencyNumber;
  final String ipV4;
  final String ipV6;


  OperatorRequestEntity(Object id, String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipV4, String ipV6) {
    this._id = id;
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipV4 = ipV4;
    this.ipV6 = ipV6;
  }
}
