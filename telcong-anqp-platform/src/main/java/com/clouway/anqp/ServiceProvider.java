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

  public ServiceProvider(ID id, String name, String description, List<Network3GPP> networks) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
  }
}
