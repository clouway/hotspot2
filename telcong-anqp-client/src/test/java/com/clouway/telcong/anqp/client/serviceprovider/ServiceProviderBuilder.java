package com.clouway.telcong.anqp.client.serviceprovider;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class ServiceProviderBuilder {
  private Object id = "id";
  private String name = "name";
  private String description = "description";
  private List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
  private List<String> domainNames = Lists.newArrayList("dName");
  private List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "organizationID"));
  private List<NAI> nais = Lists.newArrayList(new NAI("name", "UTF-8", Lists.newArrayList(new EAP("method", Lists.newArrayList(new Auth("info", "type"))))));

  public static ServiceProviderBuilder newServiceProvider() {
    return new ServiceProviderBuilder();
  }

  public ServiceProviderBuilder id(Object id) {
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

  public ServiceProviderBuilder domainNames(String... domainNames) {
    this.domainNames = Lists.newArrayList(domainNames);
    return this;
  }

  public ServiceProviderBuilder name(RoamingConsortium... consortiums) {
    this.consortiums = Lists.newArrayList(consortiums);
    return this;
  }

  public ServiceProviderBuilder naiRealms(NAI... nais) {
    this.nais = Lists.newArrayList(nais);
    return this;
  }

  public ServiceProvider build() {
    return new ServiceProvider(id, name, description, networks, domainNames, consortiums, nais);
  }
}
