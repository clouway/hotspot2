package com.clouway.anqp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class ServiceProviderBuilder {
  private ID id = new ID("id");
  private String name = "name";
  private String description = "description";
  private List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
  private DomainNameList domainNames = new DomainNameList(Lists.<String>newArrayList());
  private List<RoamingConsortium> consortiums = Lists.newArrayList();
  private List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));

  public static ServiceProviderBuilder newProvider() {
    return new ServiceProviderBuilder();
  }

  public ServiceProviderBuilder id(ID id) {
    this.id = id;
    return this;
  }

  public ServiceProviderBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ServiceProviderBuilder description(String description) {
    this.description = description;
    return this;
  }

  public ServiceProviderBuilder networks(Network3GPP... networks) {
    this.networks = Lists.newArrayList(networks);
    return this;
  }

  public ServiceProviderBuilder domainNames(DomainNameList domainNames) {
    this.domainNames = domainNames;
    return this;
  }

  public ServiceProviderBuilder consortiums(RoamingConsortium... consortiumList) {
    this.consortiums = Lists.newArrayList(consortiumList);
    return this;
  }

  public ServiceProviderBuilder naiList(NAI... naiRealms) {
    this.naiList = ImmutableList.copyOf(naiRealms);
    return this;
  }


  public ServiceProvider build() {
    return new ServiceProvider(id, name, description, networks, domainNames, consortiums, naiList);
  }
}
