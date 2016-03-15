package com.clouway.telcong.anqp.client.serviceprovider;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class ServiceProvider {
  public final Object id;
  public final String name;
  public final String description;
  public final List<Network3GPP> networks;
  public final List<String> domainNames;
  public final List<RoamingConsortium> consortiums;
  public final List<NAI> naiRealms;

  public ServiceProvider(Object id, String name, String description, List<Network3GPP> networks, List<String> domainNames, List<RoamingConsortium> consortiums, List<NAI> naiRealms) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = ImmutableList.copyOf(domainNames);
    this.consortiums = ImmutableList.copyOf(consortiums);
    this.naiRealms = ImmutableList.copyOf(naiRealms);
  }
}
