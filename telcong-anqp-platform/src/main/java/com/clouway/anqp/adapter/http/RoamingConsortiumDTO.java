package com.clouway.anqp.adapter.http;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 */
class RoamingConsortiumDTO {
  @NotNull
  final String name;
  @Pattern(regexp = "^(0x)?([0-9a-fA-F]{6}|[0-9a-fA-F]{10})$",message="Organization id should be 3 hex or 5 hex number")
  final String organizationID;

  RoamingConsortiumDTO(String name, String organizationID) {
    this.name = name;
    this.organizationID = organizationID;
  }
}
