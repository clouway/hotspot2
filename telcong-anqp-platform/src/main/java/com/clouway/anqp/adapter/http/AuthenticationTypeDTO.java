package com.clouway.anqp.adapter.http;

/**
 */
class AuthenticationTypeDTO {
  final Integer value;
  final String meaning;

  AuthenticationTypeDTO(Integer value, String meaning) {
    this.value = value;
    this.meaning = meaning;
  }
}
