package com.clouway.anqp.adapter.http;

import org.apache.bval.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 */
class VenueNameDTO {
  @Size(max = 100)
  final String name;
  @NotEmpty
  final String language;

  VenueNameDTO(String name, String language) {
    this.name = name;
    this.language = language;
  }
}
