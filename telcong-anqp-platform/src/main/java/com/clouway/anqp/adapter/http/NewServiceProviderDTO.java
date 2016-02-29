package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

/**
 */
class NewServiceProviderDTO {
  @NotEmpty
  @NotNull
  final String name;
  final String description;

  public NewServiceProviderDTO(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
