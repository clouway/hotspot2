package com.clouway.anqp.adapter.http;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 */
class NewEmergencyNumberDTO {
  @NotNull
  @NotEmpty
  final String value;

  NewEmergencyNumberDTO(String value) {
    this.value = value;
  }
}
