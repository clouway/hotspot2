package com.clouway.anqp;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 */
public final class MacAddress {
  public final String value;

  public MacAddress(String value) {
    this.value = format(value);
  }

  private String format(String value) {
    String normalized = value.toLowerCase().replaceAll("(\\.|\\,|\\:|\\-)", "");

    return Joiner.on(':').join(Splitter.fixedLength(2).split(normalized));
  }
}