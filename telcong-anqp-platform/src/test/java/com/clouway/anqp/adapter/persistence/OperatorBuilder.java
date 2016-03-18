package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ID;
import com.clouway.anqp.IPv4;
import com.clouway.anqp.IPv6;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorState;

/**
 */
public class OperatorBuilder {
  private ID id;
  private String name;
  private OperatorState state;
  private String description;
  private String domainName;
  private String friendlyName;
  private String emergencyNumber;
  private IPv4 ipV4;
  private IPv6 ipV6;

  public static OperatorBuilder operator() {
    return new OperatorBuilder();
  }

  public OperatorBuilder id(Object id) {
    this.id = new ID(id);
    return this;
  }

  public OperatorBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OperatorBuilder state(OperatorState state) {
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

  public OperatorBuilder ipV4(IPv4 ipV4) {
    this.ipV4 = ipV4;
    return this;
  }

  public OperatorBuilder ipV6(IPv6 ipV6) {
    this.ipV6 = ipV6;
    return this;
  }

  public Operator build() {
    return new Operator(id, name, state, description, domainName, friendlyName, emergencyNumber, ipV4, ipV6);
  }
}
