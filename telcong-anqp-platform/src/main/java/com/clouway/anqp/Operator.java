package com.clouway.anqp;

/**
 */
public class Operator {
  public final ID id;
  public final String name;
  public final OperatorState state;
  public final String description;
  public final String domainName;
  public final String friendlyName;
  public final String emergencyNumber;

  public Operator(ID id, String name, OperatorState state, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.id = id;
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
