package com.clouway.telcong.anqp.client.rg;

import com.clouway.telcong.anqp.client.operator.Operator;
import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class RoamingGroupBuilder {
  private Object id = "id";
  private String name = "name";
  private String description = "descr";
  private String type = "type";
  private List<Operator> operators = Lists.newArrayList(new Operator("id", "name", "state", "descr", "dName", "fName", "112", "ipV4", "ipV6"));

  public static RoamingGroupBuilder roamingGroup() {
    return new RoamingGroupBuilder();
  }

  public RoamingGroupBuilder id(Object id) {
    this.id = id;
    return this;
  }

  public RoamingGroupBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RoamingGroupBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RoamingGroupBuilder type(String type) {
    this.type = type;
    return this;
  }

  public RoamingGroupBuilder operators(Operator... operators) {
    this.operators = Lists.newArrayList(operators);
    return this;
  }

  public RoamingGroup build() {
    return new RoamingGroup(id, name, description, type, operators);
  }
}
