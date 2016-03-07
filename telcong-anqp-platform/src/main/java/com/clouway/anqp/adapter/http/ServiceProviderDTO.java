package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;
import org.apache.bval.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 */
class ServiceProviderDTO {
  final Object id;
  @NotNull
  @NotEmpty
  final String name;
  final String description;
  @Valid
  final List<Network3GPPDTO> networks;

  public ServiceProviderDTO(Object id, String name, String description, List<Network3GPPDTO> networks) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
  }
}
