package com.clouway.anqp;

import com.google.common.collect.Lists;

import java.util.List;

public class CapMessageBuilder {
  private ID id = new ID(1);
  private List<CapResource> resources = Lists.newArrayList(new CapResource("resource1"));
  private List<CapArea> areas = Lists.newArrayList(new CapArea("area1"));
  private List<CapInfo> infoList = Lists.newArrayList(new CapInfo(resources, areas));

  private CapMessageBuilder() {
  }

  public static CapMessageBuilder newCapMessage() {
    return new CapMessageBuilder();
  }

  public CapMessage build() {
    return new CapMessage(id, new CapAlert(infoList));
  }
}
