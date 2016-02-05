package com.clouway.anqp;

/**
 */
public class NewOperator {
  public final String name;
  public final String description;
  public final String domainName;
  public final String friendlyName;
  public final String emergencyNumber;

  public NewOperator(String name, String description, String domainName, String friendlyName, String emergencyNumber) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
  }
}
