package com.clouway.anqp.adapter.http;

import com.clouway.anqp.Auth.Type;
import com.clouway.anqp.AuthEntry;
import com.clouway.anqp.EAP.Method;
import com.clouway.anqp.EapAuthCatalog;
import com.clouway.anqp.EapMethodCatalog;
import com.clouway.anqp.Encoding;
import com.clouway.anqp.EncodingCatalog;
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
@At("/r/nai-realms")
public class NaiRealmEndpoint {
  private final EncodingCatalog encodingCatalog;
  private final EapMethodCatalog methodCatalog;
  private final EapAuthCatalog authCatalog;

  @Inject
  public NaiRealmEndpoint(EncodingCatalog encodingCatalog, EapMethodCatalog methodCatalog, EapAuthCatalog authCatalog) {
    this.encodingCatalog = encodingCatalog;
    this.methodCatalog = methodCatalog;
    this.authCatalog = authCatalog;
  }

  @Get
  @At("/encodings")
  public Reply<?> fetchEncodings() {
    List<Encoding> encodings = encodingCatalog.findAll();

    List<String> dto = adapt(encodings);

    return Reply.with(dto).as(Json.class).ok();
  }

  @Get
  @At("/eap-methods")
  public Reply<?> fetchEapMethods() {
    List<Method> methods = methodCatalog.findAll();

    List<String> dtos = adaptEapMethods(methods);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/auths")
  public Reply<?> fetchEapAuthentications() {
    List<AuthEntry> auths = authCatalog.findAll();

    List<AuthEntryDTO> dtos = adaptAuthEntries(auths);

    return Reply.with(dtos).as(Json.class).ok();
  }

  private List<String> adaptEapMethods(List<Method> methods) {
    List<String> list = Lists.newArrayList();

    for (Method method : methods) {
      list.add(method.name());
    }

    return list;
  }

  private List<String> adapt(List<Encoding> encodings) {
    List<String> list = Lists.newArrayList();

    for (Encoding encoding : encodings) {
      list.add(encoding.name());
    }
    return list;
  }

  private List<AuthEntryDTO> adaptAuthEntries(List<AuthEntry> items) {
    List<AuthEntryDTO> dtos = Lists.newArrayList();

    for (AuthEntry item : items) {
      dtos.add(new AuthEntryDTO(item.getInfo().name(), adaptAuthTypes(item.getTypes())));
    }

    return dtos;
  }

  private List<String> adaptAuthTypes(List<Type> types) {
    List<String> list = Lists.newArrayList();

    for (Type type : types) {
      list.add(type.name());
    }

    return list;
  }
}
