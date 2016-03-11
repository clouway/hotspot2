package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

class CapInfoDTO {
  final List<String> areas;
  final List<String> resources;

  CapInfoDTO(List<String> resources, List<String> areas) {
    this.resources = ImmutableList.copyOf(resources);
    this.areas = ImmutableList.copyOf(areas);
  }
}
