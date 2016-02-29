package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("sproviders")
class NewServiceProviderEntity {
  String name;
  String description;

  NewServiceProviderEntity(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
