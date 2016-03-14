package com.clouway.telcong.anqp.client.emergency;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class CapAlert {
  public final List<CapInfo> infos;

  public CapAlert(List<CapInfo> infos) {
    this.infos = ImmutableList.copyOf(infos);
  }
}
