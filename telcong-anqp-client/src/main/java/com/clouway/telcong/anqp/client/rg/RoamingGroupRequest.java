package com.clouway.telcong.anqp.client.rg;

/**
 */
public class RoamingGroupRequest {
  public final Object id;
  public final String name;
  public final String description;
  public final String type;

  public RoamingGroupRequest(Object id, String name, String description, String type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
  }
}
