package com.clouway.telcong.anqp.client.rg;

/**
 */
public class NewRoamingGroupBuilder {
  private String name = "name";
  private String description = "descr";
  private String type = "type";

  public static NewRoamingGroupBuilder newRG() {
    return new NewRoamingGroupBuilder();
  }

  public NewRoamingGroupBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NewRoamingGroupBuilder description(String description) {
    this.description = description;
    return this;
  }

  public NewRoamingGroupBuilder type(String type) {
    this.type = type;
    return this;
  }

  public NewRoamingGroup build() {
    return new NewRoamingGroup(name, description, type);
  }
}
