package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IpTypeCatalog;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import java.util.List;

@Service
@At("/r/ip-types")
public class IpTypeService {
  private final IpTypeCatalog catalog;

  @Inject
  public IpTypeService(IpTypeCatalog catalog) {
    this.catalog = catalog;
  }

  @Get
  public Reply<?> getAll() {
    List<String> types = catalog.getAll();

    return Reply.with(types).as(Json.class).ok();
  }

}
