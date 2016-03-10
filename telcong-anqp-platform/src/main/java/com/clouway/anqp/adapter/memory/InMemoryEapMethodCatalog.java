package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.EapMethodCatalog;
import com.clouway.anqp.EAP.Method;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class InMemoryEapMethodCatalog implements EapMethodCatalog {
  private final List<Method> methods;

  InMemoryEapMethodCatalog(Method... methods) {
    this.methods = ImmutableList.copyOf(methods);
  }

  @Override
  public List<Method> findAll() {
    return methods;
  }
}
