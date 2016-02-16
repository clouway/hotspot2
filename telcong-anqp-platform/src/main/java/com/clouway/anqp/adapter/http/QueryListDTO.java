package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

class QueryListDTO {
  final List<Integer> values;

  QueryListDTO(List<Integer> values) {
    this.values = ImmutableList.copyOf(values);
  }
}
