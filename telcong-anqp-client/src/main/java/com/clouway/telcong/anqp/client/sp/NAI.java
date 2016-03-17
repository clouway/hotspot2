package com.clouway.telcong.anqp.client.sp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class NAI {
  public final String name;
  public final String encoding;
  public final List<EAP> eaps;

  public NAI(String name, String encoding, List<EAP> eaps) {
    this.name = name;
    this.encoding = encoding;
    this.eaps = ImmutableList.copyOf(eaps);
  }
}
