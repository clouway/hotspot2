package com.clouway.anqp;

/**
 */
public class ServiceProvider {
  public final ID id;
  public final String name;
  public final String description;

  public ServiceProvider(ID id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}
