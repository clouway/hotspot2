package com.clouway.anqp.adapter.persistence;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class EapEntity {
  String method;
  List<AuthEntity> auths;

  EapEntity() {
  }

  EapEntity(String method, List<AuthEntity> auths) {
    this.method = method;
    this.auths = ImmutableList.copyOf(auths);
  }
}
