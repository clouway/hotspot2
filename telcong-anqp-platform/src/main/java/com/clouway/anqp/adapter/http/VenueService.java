package com.clouway.anqp.adapter.http;

import com.clouway.anqp.VenueItem;
import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
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
@At("/r/venues")
public class VenueService {
  private final VenueFinder finder;

  @Inject
  public VenueService(VenueFinder finder) {
    this.finder = finder;
  }

  @Get
  public Reply<?> fetchAll() {
    List<VenueItem> items = finder.findAll();

    List<VenueItemDTO> dto = adapt(items);

    return Reply.with(dto).as(Json.class).ok();
  }

  private List<VenueItemDTO> adapt(List<VenueItem> venueItem) {
    List<VenueItemDTO> dto = Lists.newArrayList();

    for (VenueItem info : venueItem) {
      dto.add(new VenueItemDTO(info.group.name, adapt(info.types)));
    }

    return dto;
  }

  private List<String> adapt(VenueTypeList list) {
    List<String> dto = Lists.newArrayList();

    for (VenueType type : list.values()) {
      dto.add(type.name);
    }

    return dto;
  }
}