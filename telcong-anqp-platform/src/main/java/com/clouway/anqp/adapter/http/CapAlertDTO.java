package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

class CapAlertDTO {
  final List<CapInfoDTO> infos;

  CapAlertDTO(List<CapInfoDTO> infos) {
    this.infos = ImmutableList.copyOf(infos);
  }
}
