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

  NewOperatorEntity(String name, String description, String domainName, String friendlyName) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
  }
}
