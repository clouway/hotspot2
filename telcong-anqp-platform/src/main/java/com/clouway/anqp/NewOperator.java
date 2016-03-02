package com.clouway.anqp;

/**
 */
public class NewOperator {
  public final String name;
  public final OperatorState state;
  public final String description;
  public final String domainName;
  public final String friendlyName;
  public final String emergencyNumber;
  public final IPv4 ipV4;
  public final IPv6 ipV6;

  public NewOperator(String name, OperatorState state, String description, String domainName, String friendlyName, String emergencyNumber, IPv4 ipV4, IPv6 ipV6) {
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
