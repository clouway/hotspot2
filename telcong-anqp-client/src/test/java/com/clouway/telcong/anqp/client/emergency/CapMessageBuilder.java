package com.clouway.telcong.anqp.client.emergency;

import com.google.common.collect.Lists;

/**
 */
public class CapMessageBuilder {
  private Object id = "id";
  private CapAlert alert = new CapAlert(
          Lists.newArrayList(new CapInfo(Lists.newArrayList("resources"), Lists.newArrayList("areas")))
  );

  public static CapMessageBuilder newCapMessage() {
    return new CapMessageBuilder();
  }

  public CapMessageBuilder id(Object id) {
    this.id = id;
    return this;
  }

  public CapMessageBuilder alert(CapAlert alert) {
    this.alert = alert;
    return this;
  }

  public CapMessage build() {
    return new CapMessage(id, alert);
  }

}
