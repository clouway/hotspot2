package com.clouway.telcong.anqp.client.sp;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class NewServiceProviderBuilder {
  private String name = "name";
  private String description = "description";
  private List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
  private List<String> domainNames = Lists.newArrayList("dName");
  private List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "organizationID"));
  private List<NAI> nais = Lists.newArrayList(new NAI("name", "UTF-8", Lists.newArrayList(new EAP("method", Lists.newArrayList(new Auth("info", "type"))))));

  public static NewServiceProviderBuilder newProvider() {
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

  public NewServiceProviderBuilder domainNames(String... domainNames) {
    this.domainNames = Lists.newArrayList(domainNames);
    return this;
  }

  public NewServiceProviderBuilder name(RoamingConsortium... consortiums) {
    this.consortiums = Lists.newArrayList(consortiums);
    return this;
  }

  public NewServiceProviderBuilder naiRealms(NAI... nais) {
    this.nais = Lists.newArrayList(nais);
    return this;
  }

  public NewServiceProvider build() {
    return new NewServiceProvider(name, description, networks, domainNames, consortiums, nais);
  }
}
