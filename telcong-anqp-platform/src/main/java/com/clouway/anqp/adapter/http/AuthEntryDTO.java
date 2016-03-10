package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class AuthEntryDTO {
   final String info;
   final List<String> types;

  AuthEntryDTO(String info, List<String> types) {
    this.info = info;
    this.types = ImmutableList.copyOf(types);
  }
}
