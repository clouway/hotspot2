package com.clouway.anqp.adapter.http;

/**
 */
class AuthenticationTypeDTO {
  Integer value;
  String meaning;

  AuthenticationTypeDTO(Integer value, String meaning) {
    this.value = value;
    this.meaning = meaning;
  }
}
