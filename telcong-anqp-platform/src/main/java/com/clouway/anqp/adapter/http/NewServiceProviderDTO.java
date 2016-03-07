package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;
import org.apache.bval.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import java.util.List;

/**
 */
class NewServiceProviderDTO {
  @NotEmpty
  @NotNull
  final String name;
  final String description;
  @Valid
  final List<Network3GPPDTO> networks;

  public NewServiceProviderDTO(String name, String description, List<Network3GPPDTO> networks) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
  }
}
