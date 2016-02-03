package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Id;
import com.clouway.anqp.api.datastore.Kind;

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

  @SuppressWarnings("unused")
  OperatorEntity() {
  }

  OperatorEntity(Object id, String name, String description, String domainName, String friendlyName) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
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
}
