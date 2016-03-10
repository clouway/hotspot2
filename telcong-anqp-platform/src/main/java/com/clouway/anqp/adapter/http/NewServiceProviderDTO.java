package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;
import org.apache.bval.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.Size;
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
  @Valid
  final List<RoamingConsortiumDTO> consortiums;
  @Valid
  @Size(max = 2)
  final List<NaiDTO> naiRealms;

  public NewServiceProviderDTO(String name, String description, List<Network3GPPDTO> networks, List<String> domainNames, List<RoamingConsortiumDTO> consortiums, List<NaiDTO> naiRealms) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = ImmutableList.copyOf(domainNames);
    this.consortiums = ImmutableList.copyOf(consortiums);
    this.naiRealms = ImmutableList.copyOf(naiRealms);
  }
}
