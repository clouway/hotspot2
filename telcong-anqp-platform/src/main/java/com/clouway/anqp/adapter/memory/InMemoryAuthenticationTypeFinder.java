package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.AuthenticationType;
import com.clouway.anqp.AuthenticationTypeFinder;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class InMemoryAuthenticationTypeFinder implements AuthenticationTypeFinder {
  private final List<AuthenticationType> types;

  InMemoryAuthenticationTypeFinder(AuthenticationType... types) {
    this.types = ImmutableList.copyOf(types);
  }

  @Override
  public List<AuthenticationType> findAll() {
    return types;
  }
}
