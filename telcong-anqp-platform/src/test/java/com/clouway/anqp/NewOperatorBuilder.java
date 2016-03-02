package com.clouway.anqp;

/**
 */
public class NewOperatorBuilder {
  private String name = "name";
  private OperatorState state = OperatorState.ACTIVE;
  private String description = "description";
  private String domainName = "dName";
  private String friendlyName = "fName";
  private String emergencyNumber = "112";
  private IPv4 ipV4 = new IPv4(IPv4.Availability.UNKNOWN);
  private IPv6 ipV6 = new IPv6(IPv6.Availability.UNKNOWN);

  public static NewOperatorBuilder newOperator() {
    return new NewOperatorBuilder();
  }

  public NewOperatorBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NewOperatorBuilder state(OperatorState state) {
    this.state = state;
    return this;
  }

  public NewOperatorBuilder description(String description) {
    this.description = description;
    return this;
  }

  public NewOperatorBuilder domainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  public NewOperatorBuilder friendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
    return this;
  }

  public NewOperatorBuilder emergencyNumber(String emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
    return this;
  }

  public NewOperatorBuilder iPv4(IPv4 ip) {
    this.ipV4 = ip;
    return this;
  }

  public NewOperatorBuilder iPv6(IPv6 ip) {
    this.ipV6 = ip;
    return this;
  }

  public NewOperator build() {
    return new NewOperator(name, state, description, domainName, friendlyName, emergencyNumber, ipV4, ipV6);
  }
}
