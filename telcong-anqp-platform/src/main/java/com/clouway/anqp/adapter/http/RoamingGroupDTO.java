package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 */
class RoamingGroupDTO {
  final Object id;
  final String name;
  final String description;
  final String type;
  final List<OperatorDTO> operators;

  RoamingGroupDTO(Object id, String name, String description, String type, List<OperatorDTO> operators) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.operators = ImmutableList.copyOf(operators);
  }
}
