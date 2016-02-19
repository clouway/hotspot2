package com.clouway.anqp.adapter.persistence;

final class CapabilityEntity {
  Integer id;
  String name;

  public CapabilityEntity() {
  }

  public CapabilityEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}