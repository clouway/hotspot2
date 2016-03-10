package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.Encoding;
import com.clouway.anqp.EncodingCatalog;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class InMemoryEncodingCatalog implements EncodingCatalog {
  private final List<Encoding> encodings;

  InMemoryEncodingCatalog(Encoding... encodings) {
    this.encodings = ImmutableList.copyOf(encodings);
  }

  @Override
  public List<Encoding> findAll() {
    return encodings;
  }
}
