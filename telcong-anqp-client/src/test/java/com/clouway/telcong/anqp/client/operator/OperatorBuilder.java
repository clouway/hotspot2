package com.clouway.telcong.anqp.client.operator;

/**
 */
public class OperatorBuilder {
  private Object id = "id";
  private String name = "name";
  private String state = "state";
  private String description = "descr";
  private String domainName = "dName";
  private String friendlyName = "fName";
  private String emergencyNumber = "emergency";
  private String ipV4 = "ipV4";
  private String ipV6 = "ipV6";

  public static OperatorBuilder operator() {
    return new OperatorBuilder();
  }

  public OperatorBuilder id(Object id) {
    this.id = id;
    return this;
  }

  public OperatorBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OperatorBuilder state(String state) {
    this.state = state;
    return this;
  }

  public OperatorBuilder description(String description) {
    this.description = description;
    return this;
  }

  public OperatorBuilder domainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  public OperatorBuilder friendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
    return this;
  }

  public OperatorBuilder emergency(String emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
    return this;
  }

  public OperatorBuilder ipV4(String ipV4) {
    this.ipV4 = ipV4;
    return this;
  }

  public OperatorBuilder ipV6(String ipV6) {
    this.ipV6 = ipV6;
    return this;
  }

  public Operator build() {
    return new Operator(id, name, state, description, domainName, friendlyName, emergencyNumber, ipV4, ipV6);
  }
}
