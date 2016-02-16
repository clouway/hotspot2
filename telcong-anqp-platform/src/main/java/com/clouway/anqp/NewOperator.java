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
  public final IpType ipType;

  public NewOperator(String name, OperatorState state, String description, String domainName, String friendlyName, String emergencyNumber, IpType ipType) {
    this.name = name;
    this.state = state;
    this.description = description;
    this.domainName = domainName;
    this.friendlyName = friendlyName;
    this.emergencyNumber = emergencyNumber;
    this.ipType = ipType;
  }
}
