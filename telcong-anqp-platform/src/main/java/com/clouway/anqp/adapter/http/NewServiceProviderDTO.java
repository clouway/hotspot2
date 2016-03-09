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
  final List<String> domainNames;

  public NewServiceProviderDTO(String name, String description, List<Network3GPPDTO> networks, List<String> domainNames) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = ImmutableList.copyOf(domainNames);
  }
}
