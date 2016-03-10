package com.clouway.anqp.adapter.persistence;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class NaiEntity {
  String name;
  String encoding;
  List<EapEntity> eaps;

  NaiEntity() {
  }

  NaiEntity(String name, String encoding, List<EapEntity> eaps) {
    this.name = name;
    this.encoding = encoding;
    this.eaps = ImmutableList.copyOf(eaps);
  }
}
