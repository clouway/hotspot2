package com.clouway.anqp.adapter.http;

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
  private final EncodingCatalog catalog;

  @Inject
  public NaiRealmEndpoint(EncodingCatalog catalog) {
    this.catalog = catalog;
  }

  @Get
  @At("/encodings")
  public Reply<?> fetchEncodings() {
    List<Encoding> encodings = catalog.findAll();

    List<String> dto = adapt(encodings);

    return Reply.with(dto).as(Json.class).ok();
  }

  private List<String> adapt(List<Encoding> encodings) {
    List<String> list = Lists.newArrayList();

    for (Encoding encoding : encodings) {
      list.add(encoding.name());
    }
    return list;
  }
}
