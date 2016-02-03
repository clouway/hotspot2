package com.clouway.anqp;

/**
 */
public class NewOperator {
  public final String name;
  public final String description;
  public final String domainName;
  public final String friendlyName;

  public NewOperator(String name, String description, String domainName, String friendlyName) {
    this.name = name;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
  }
}
