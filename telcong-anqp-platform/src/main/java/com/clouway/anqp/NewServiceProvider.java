package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class NewServiceProvider {
  public final String name;
  public final String description;
  public final List<Network3GPP> networks;

  public NewServiceProvider(String name, String description, List<Network3GPP> networks) {
    this.name = name;
    this.description = description;
    this.networks = ImmutableList.copyOf(networks);
  }
}
