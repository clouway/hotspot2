package com.clouway.telcong.anqp.client.emergency;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class CapInfo {
  public final List<String> areas;
  public final List<String> resources;

  public CapInfo(List<String> resources, List<String> areas) {
    this.resources = ImmutableList.copyOf(resources);
    this.areas = ImmutableList.copyOf(areas);
  }
}
