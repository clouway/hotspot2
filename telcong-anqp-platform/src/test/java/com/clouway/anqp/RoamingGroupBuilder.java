package com.clouway.anqp;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class RoamingGroupBuilder {
  private ID id = new ID(1);
  private String name = "name";
  private String description = "description";
  private RoamingGroupType type = RoamingGroupType.NATIONAL;
  private List<Operator> operators = Lists.newArrayList();

  public static RoamingGroupBuilder newRoamingGroup() {
    return new RoamingGroupBuilder();
  }

  public RoamingGroupBuilder id(ID id) {
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

  public RoamingGroupBuilder type(RoamingGroupType type) {
    this.type = type;
    return this;
  }

  public RoamingGroupBuilder opeators(List<Operator> operators) {
    this.operators = operators;
    return this;
  }

  public RoamingGroup build() {
    return new RoamingGroup(id, name, description, type, operators);
  }

}
