package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv4;
import com.clouway.anqp.IPv4.Availability;
import com.clouway.anqp.IPv4AvailabilityCatalog;
import com.clouway.anqp.IPv6;
import com.clouway.anqp.IPv6AvailabilityCatalog;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import java.util.List;

@Service
@At("/r/ip-availabilities")
public class IpAvailabilityEndpoint {
  private final IPv4AvailabilityCatalog v4Catalog;
  private final IPv6AvailabilityCatalog v6Catalog;

  @Inject
  public IpAvailabilityEndpoint(IPv4AvailabilityCatalog v4Catalog, IPv6AvailabilityCatalog v6Catalog) {
    this.v4Catalog = v4Catalog;
    this.v6Catalog = v6Catalog;
  }

  @Get
  @At("/v4")
  public Reply<?> findV4() {
    List<Availability> availabilities = v4Catalog.findAll();

    List<String> dto = adaptV4(availabilities);

    return Reply.with(dto).as(Json.class).ok();
  }

  @Get
  @At("/v6")
  public Reply<?> findV6() {
    List<IPv6.Availability> availabilities = v6Catalog.findAll();

    List<String> dto = adaptV6(availabilities);

    return Reply.with(dto).as(Json.class).ok();
  }

  private List<String> adaptV4(List<IPv4.Availability> availabilities) {
    List<String> list = Lists.newArrayList();

    for (Availability availability : availabilities) {
      list.add(availability.name());
    }

    return list;
  }

  private List<String> adaptV6(List<IPv6.Availability> availabilities) {
    List<String> list = Lists.newArrayList();

    for (IPv6.Availability availability : availabilities) {
      list.add(availability.name());
    }

    return list;
  }
}
