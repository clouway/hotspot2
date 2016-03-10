package com.clouway.anqp.adapter.http;

/**
 */
@ValidAuth
class AuthDTO {
  final String info;
  final String type;

  AuthDTO(String info, String type) {
    this.info = info;
    this.type = type;
  }
}
