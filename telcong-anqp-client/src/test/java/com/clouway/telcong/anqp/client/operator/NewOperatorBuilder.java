package com.clouway.telcong.anqp.client.operator;

/**
 */
public class NewOperatorBuilder {
  private String name = "name";
  private String state = "state";
  private String description = "descr";
  private String domainName = "dName";
  private String friendlyName = "fName";
  private String emergencyNumber = "emergency";
  private String ipV4 = "ipV4";
  private String ipV6 = "ipV6";

  public static NewOperatorBuilder newOperator() {
    return new NewOperatorBuilder();
  }

  public NewOperatorBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NewOperatorBuilder state(String state) {
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

  public NewOperatorBuilder emergency(String emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
    return this;
  }

  public NewOperatorBuilder ipV4(String ipV4) {
    this.ipV4 = ipV4;
    return this;
  }

  public NewOperatorBuilder ipV6(String ipV6) {
    this.ipV6 = ipV6;
    return this;
  }

  public NewOperator build() {
    return new NewOperator(name, state, description, domainName, friendlyName, emergencyNumber, ipV4, ipV6);
  }
}
