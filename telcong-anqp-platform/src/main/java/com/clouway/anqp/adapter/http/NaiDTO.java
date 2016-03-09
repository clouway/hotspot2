package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 */
class NaiDTO {
  @NotEmpty
  @NotNull
  final String name;

  NaiDTO(String name) {
    this.name = name;
  }
}
