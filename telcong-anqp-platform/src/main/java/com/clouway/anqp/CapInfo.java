package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * The info segment describes an anticipated or actual event
 */
public final class CapInfo {
  public final List<CapArea> areas;
  public final List<CapResource> resources;

  public CapInfo(List<CapResource> resources, List<CapArea> areas) {
    this.resources = ImmutableList.copyOf(resources);
    this.areas = ImmutableList.copyOf(areas);
  }
}
