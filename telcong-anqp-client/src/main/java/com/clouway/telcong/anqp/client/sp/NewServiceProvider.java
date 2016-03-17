package com.clouway.telcong.anqp.client.sp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class NewServiceProvider {
  public final String name;
  public final String description;
  public final List<Network3GPP> networks;
  public final List<String> domainNames;
  public final List<RoamingConsortium> consortiums;
  public final List<NAI> naiRealms;

  public NewServiceProvider(String name, String description, List<Network3GPP> networks, List<String> domainNames, List<RoamingConsortium> consortiums, List<NAI> naiRealms) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
    this.domainNames = ImmutableList.copyOf(domainNames);
    this.consortiums = ImmutableList.copyOf(consortiums);
    this.naiRealms = ImmutableList.copyOf(naiRealms);
  }
}
