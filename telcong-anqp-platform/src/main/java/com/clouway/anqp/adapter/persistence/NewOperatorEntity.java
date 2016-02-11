package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("operators")
class NewOperatorEntity {
   String name;
   String description;
   String domainName;
   String friendlyName;
   String emergencyNumber;

  NewOperatorEntity(String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
