package com.clouway.anqp;

/**
 */
public class Operator {
  public final Object id;
  public final String name;
  public final String description;
  public final String domainName;
  public final String friendlyName;
  public final String emergencyNumber;

  public Operator(Object id, String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}