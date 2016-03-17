package com.clouway.telcong.anqp.client.rg;

import com.clouway.telcong.anqp.client.operator.Operator;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
public class RoamingGroup {
  public final Object id;
  public final String name;
  public final String description;
  public final String type;
  public final List<Operator> operators;

  public RoamingGroup(Object id, String name, String description, String type, List<Operator> operators) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.operators = ImmutableList.copyOf(operators);
  }
}
