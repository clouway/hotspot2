package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.api.datastore.Kind;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
@Kind("sproviders")
class NewServiceProviderEntity {
  String name;
  String description;
  List<Network3GPPEntity> networks;

  NewServiceProviderEntity(String name, String description, List<Network3GPPEntity> networks) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
  }
}
