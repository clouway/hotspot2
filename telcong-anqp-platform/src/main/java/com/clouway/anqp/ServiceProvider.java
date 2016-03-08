package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class ServiceProvider {
  public final ID id;
  public final String name;
  public final String description;
  public final List<Network3GPP> networks;
  public final DomainNameList domainNames;
  public final List<RoamingConsortium> consortiums;

  public ServiceProvider(ID id, String name, String description, List<Network3GPP> networks, DomainNameList domainNames, List<RoamingConsortium> consortiums) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = domainNames;
    this.consortiums = ImmutableList.copyOf(consortiums);
  }
}
