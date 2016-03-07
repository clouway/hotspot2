package com.clouway.anqp;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class ServiceProviderBuilder {
  private ID id = new ID("id");
  private String name = "name";
  private String description = "description";
  private List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));

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

  public ServiceProvider build() {
    return new ServiceProvider(id, name, description, networks);
  }
}
