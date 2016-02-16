package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class VenueItemDTO {
   final String group;
   final List<String> types;

  VenueItemDTO(String group, List<String> types) {
    this.group = group;
    this.types = ImmutableList.copyOf(types);
  }
}
