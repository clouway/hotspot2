package com.clouway.anqp.adapter.http;

import com.clouway.anqp.AuthenticationType;
import com.clouway.anqp.AuthenticationTypeFinder;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import java.util.List;

/**
 */
@Service
@At("/r/auth-types")
class AuthTypeEndpoint {
  private final AuthenticationTypeFinder finder;

  @Inject
  AuthTypeEndpoint(AuthenticationTypeFinder finder) {
    this.finder = finder;
  }

  @Get
  public Reply<?> findAll() {
    List<AuthenticationType> types = finder.findAll();

    List<AuthenticationTypeDTO> dtos = adapt(types);

    return Reply.with(dtos).as(Json.class).ok();
  }

  private List<AuthenticationTypeDTO> adapt(List<AuthenticationType> types) {
    List<AuthenticationTypeDTO> dtos = Lists.newArrayList();

    for (AuthenticationType type : types) {
      dtos.add(new AuthenticationTypeDTO(type.value, type.meaning));
    }

    return dtos;
  }
}
