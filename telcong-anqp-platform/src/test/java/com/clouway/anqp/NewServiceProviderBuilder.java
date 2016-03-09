package com.clouway.anqp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class NewServiceProviderBuilder {
  private String name = "names";
  private String description = "description";
  private List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
  private DomainNameList domainNames = new DomainNameList(Lists.<String>newArrayList());
  private List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "0xAABBFF"));
  private List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));

  public static NewServiceProviderBuilder newServiceProvider() {
    return new NewServiceProviderBuilder();
  }

  public NewServiceProviderBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NewServiceProviderBuilder description(String description) {
    this.description = description;
    return this;
  }

  public NewServiceProviderBuilder networks(Network3GPP... networks) {
    this.networks = Lists.newArrayList(networks);
    return this;
  }

  public NewServiceProviderBuilder domainNames(DomainNameList domainNames) {
    this.domainNames = domainNames;
    return this;
  }

  public NewServiceProviderBuilder consortiums(RoamingConsortium... consortiumList) {
    this.consortiums = Lists.newArrayList(consortiumList);
    return this;
  }

  public NewServiceProviderBuilder naiList(NAI... naiRealms) {
    this.naiList = ImmutableList.copyOf(naiRealms);
    return this;
  }

  public NewServiceProvider build() {
    return new NewServiceProvider(name, description, networks, domainNames, consortiums, naiList);
  }
}
