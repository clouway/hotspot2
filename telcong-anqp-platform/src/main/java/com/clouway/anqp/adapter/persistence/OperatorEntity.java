package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Id;
import com.clouway.anqp.api.datastore.Kind;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 */
@Kind("operators")
class OperatorEntity {
  @Id
  private Object id;
  private String name;
  private String description;
  private String domainName;
  private String friendlyName;
  private String emergencyNumber;

  @SuppressWarnings("unused")
  OperatorEntity() {
  }

  OperatorEntity(Object id, String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = setDefaultValueIfEmpty(emergencyNumber);
  }

  Object getId() {
    return id;
  }

  String getName() {
    return name;
  }

  String getDescription() {
    return description;
  }

  String getDomainName() {
    return domainName;
  }

  String getFriendlyName() {
    return friendlyName;
  }

  String getEmergencyNumber() {
    return emergencyNumber;
  }

  private String setDefaultValueIfEmpty(String emergencyNumber) {
    if (isNullOrEmpty(emergencyNumber)) {
      return "112";
    }

    return emergencyNumber;
  }
}
