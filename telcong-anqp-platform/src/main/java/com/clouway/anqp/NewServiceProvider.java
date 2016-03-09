package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class NewServiceProvider {
  public final String name;
  public final String description;
  public final List<Network3GPP> networks;
  public final DomainNameList domainNames;

  public NewServiceProvider(String name, String description, List<Network3GPP> networks, DomainNameList domainNames) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = domainNames;
  }
}
