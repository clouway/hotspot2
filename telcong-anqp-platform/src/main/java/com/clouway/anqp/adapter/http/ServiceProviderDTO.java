package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 */
class ServiceProviderDTO {
  final Object id;
  @NotNull
  @NotEmpty
  final String name;
  final String description;

  public ServiceProviderDTO(Object id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}
