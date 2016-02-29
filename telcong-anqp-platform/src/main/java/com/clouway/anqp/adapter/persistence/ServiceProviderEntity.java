package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;

/**
 */
@Kind("sproviders")
class ServiceProviderEntity {
  Object _id;
  String name;
  String description;

  public ServiceProviderEntity() {
  }

  ServiceProviderEntity(Object id, String name, String description) {
    this._id = id;
    this.name = name;
    this.description = description;
  }
}
