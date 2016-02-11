package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 */
@Kind("operators")
class OperatorEntity {
   Object _id;
   String name;
   String description;
   String domainName;
   String friendlyName;
   String emergencyNumber;

  public OperatorEntity() {
  }

  OperatorEntity(Object id, String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = setDefaultValueIfEmpty(emergencyNumber);
  }

  private String setDefaultValueIfEmpty(String emergencyNumber) {
    if (isNullOrEmpty(emergencyNumber)) {
      return "112";
    }

    return emergencyNumber;
  }
}
