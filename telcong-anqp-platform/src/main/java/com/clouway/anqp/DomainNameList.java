package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * The Domain Name list element provides a list of one or more domain names of the entity operating the
 * IEEE 802.11 access network.
 */
public class DomainNameList {
  public final List<String> values;

  public DomainNameList(List<String> values) {
    this.values = ImmutableList.copyOf(values);
  }
}
