package com.clouway.anqp;

/**
 * The alert segment provides basic information about the current message
 */

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class CapAlert {
  public final List<CapInfo> infos;

  public CapAlert(List<CapInfo> infos) {
    this.infos = ImmutableList.copyOf(infos);
  }
}