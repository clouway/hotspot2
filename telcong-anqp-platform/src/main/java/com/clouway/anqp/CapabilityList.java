package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class CapabilityList {
  public final List<Capability> values;

  public CapabilityList(List<Capability> values) {
    this.values = ImmutableList.copyOf(values);
  }
}


