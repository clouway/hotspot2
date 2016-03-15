package com.clouway.telcong.anqp.client.ap;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class QueryList {
  public final List<Integer> values;

  public QueryList(List<Integer> values) {
    this.values = ImmutableList.copyOf(values);
  }
}
