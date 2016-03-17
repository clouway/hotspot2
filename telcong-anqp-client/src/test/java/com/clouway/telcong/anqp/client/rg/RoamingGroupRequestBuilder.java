package com.clouway.telcong.anqp.client.rg;

/**
 */
public class RoamingGroupRequestBuilder {
  private Object id = "id";
  private String name = "name";
  private String description = "descr";
  private String type = "type";

  public static RoamingGroupRequestBuilder newGroupRequest() {
    return new RoamingGroupRequestBuilder();
  }

  public RoamingGroupRequestBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RoamingGroupRequestBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RoamingGroupRequestBuilder type(String type) {
    this.type = type;
    return this;
  }

  public RoamingGroupRequest build() {
    return new RoamingGroupRequest(id, name, description, type);
  }
}
