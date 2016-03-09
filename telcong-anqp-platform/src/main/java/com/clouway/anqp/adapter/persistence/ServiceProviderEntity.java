package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
@Kind("sproviders")
class ServiceProviderEntity {
  Object _id;
  String name;
  String description;
  List<Network3GPPEntity> networks;
  List<String> domainNames;

  @SuppressWarnings("unused")
  public ServiceProviderEntity() {
  }

  ServiceProviderEntity(Object id, String name, String description, List<Network3GPPEntity> networks, List<String> domainNames) {
    this._id = id;
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = ImmutableList.copyOf(domainNames);
  }
}
