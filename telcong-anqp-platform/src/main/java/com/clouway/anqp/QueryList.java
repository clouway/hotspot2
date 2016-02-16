package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class QueryList {
  public final List<Integer> values;

  public QueryList(Integer... values) {
    this.values = ImmutableList.copyOf(values);
  }
}
