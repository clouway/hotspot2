package com.clouway.anqp;

/**
 */
public class NewRoamingGroup {
  public final String name;
  public final String description;
  public final RoamingGroupType type;

  public NewRoamingGroup(String name, String description, RoamingGroupType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
