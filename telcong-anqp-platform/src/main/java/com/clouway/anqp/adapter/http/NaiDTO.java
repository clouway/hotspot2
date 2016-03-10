package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 */
class NaiDTO {
  @NotEmpty
  @NotNull
  final String name;
  final String encoding;

  NaiDTO(String name, String encoding) {
    this.name = name;
    this.encoding = encoding;
  }
}
