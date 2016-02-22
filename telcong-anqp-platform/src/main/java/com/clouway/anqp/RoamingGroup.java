package com.clouway.anqp;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class RoamingGroup {
  public final ID id;
  public final String name;
  public final String description;
  public final RoamingGroupType type;
  public final List<Operator> operators;

  public RoamingGroup(ID id, String name, String description, RoamingGroupType type, List<Operator> operators) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.operators = ImmutableList.copyOf(operators);
  }
}
