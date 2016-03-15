package com.clouway.telcong.anqp.client.operator;

/**
 */
public class NewOperator {
  public final String name;
  public final String state;
  public final String description;
  public final String domainName;
  public final String friendlyName;
  public final String emergencyNumber;
  public final String ipV4;
  public final String ipV6;

  public NewOperator(String name, String state, String description, String domainName, String friendlyName, String emergencyNumber, String ipV4, String ipV6) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipV4 = ipV4;
    this.ipV6 = ipV6;
  }
}
