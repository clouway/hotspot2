package com.clouway.anqp;

/**
 */
public class RoamingGroupRequest {
  public final ID id;
  public final String name;
  public final String description;
  public final RoamingGroupType type;

  public RoamingGroupRequest(ID id, String name, String description, RoamingGroupType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
