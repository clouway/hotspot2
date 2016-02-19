package com.clouway.anqp.adapter.http;

import com.clouway.anqp.Capability;
import com.clouway.anqp.CapabilityCatalog;
import com.clouway.anqp.CapabilityList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import java.util.List;

@Service
@At("/r/capabilities")
public class CapabilityEndpoint {
  private final CapabilityCatalog catalog;

  @Inject
  public CapabilityEndpoint(CapabilityCatalog catalog) {
    this.catalog = catalog;
  }

  @Get
  public Reply<?> findAll() {
    CapabilityList capabilities = catalog.findAll();

    List<CapabilityDTO> dto = adapt(capabilities);

    return Reply.with(dto).as(Json.class).ok();
  }

  private List<CapabilityDTO> adapt(CapabilityList capabilities) {
    List<CapabilityDTO> dtos = Lists.newArrayList();

    for (Capability capability : capabilities.values) {
      dtos.add(new CapabilityDTO(capability.id, capability.name));
    }

    return dtos;
  }
}
