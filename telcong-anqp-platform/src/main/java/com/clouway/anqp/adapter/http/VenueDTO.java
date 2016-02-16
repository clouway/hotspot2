package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 */
@ValidVenue
class VenueDTO {
  final String group;
  final String type;

  @Valid
  @Size(max = 2)
  final List<VenueNameDTO> venueNames;

  public VenueDTO(String group, String type, List<VenueNameDTO> venueNames) {
    this.group = group;
    this.type = type;
    this.venueNames = ImmutableList.copyOf(venueNames);
  }
}
