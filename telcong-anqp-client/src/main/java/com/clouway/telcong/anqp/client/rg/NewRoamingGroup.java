package com.clouway.telcong.anqp.client.rg;

/**
 */
public class NewRoamingGroup {
  public final String name;
  public final String description;
  public final String type;

  public NewRoamingGroup(String name, String description, String type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
