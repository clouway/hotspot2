package com.clouway.telcong.anqp.client.nai;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class AuthEntry {
  public final String info;
  public final List<String> types;

  public AuthEntry(String info, List<String> types) {
    this.info = info;
    this.types = ImmutableList.copyOf(types);
  }
}
