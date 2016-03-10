package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import javax.validation.Valid;
import java.util.List;

/**
 */
class EapDTO {
  final String method;
  @Valid
  final List<AuthDTO> authentications;

  EapDTO(String method, List<AuthDTO> authentications) {
    this.method = method;
    this.authentications = ImmutableList.copyOf(authentications);
  }
}
