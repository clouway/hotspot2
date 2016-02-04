package com.clouway.anqp;

/**
 */
public class RoamingGroup {
  public final Object id;
  public final String name;
  public final String description;
  public final RoamingGroupType type;

  public RoamingGroup(Object id, String name, String description, RoamingGroupType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
