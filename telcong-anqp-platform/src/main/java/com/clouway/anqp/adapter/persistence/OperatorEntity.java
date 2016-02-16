package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 */
@Kind("operators")
class OperatorEntity {
  Object _id;
  String name;
  String state;
  String description;
  String domainName;
  String friendlyName;
  String emergencyNumber;
  String ipType;

  @SuppressWarnings("unused")
  OperatorEntity() {
  }

  OperatorEntity(Object id, String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipType) {
    this._id = id;
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = setDefaultValueIfEmpty(emergencyNumber);
    this.ipType = ipType;
  }

  private String setDefaultValueIfEmpty(String emergencyNumber) {
    if (isNullOrEmpty(emergencyNumber)) {
      return "112";
    }

    return emergencyNumber;
  }
}
