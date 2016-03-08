package com.clouway.anqp.adapter.persistence;

/**
 */
class RoamingConsortiumEntity {
  String name;
  String organizationID;

  @SuppressWarnings("unused")
  public RoamingConsortiumEntity() {

  }

  RoamingConsortiumEntity(String name, String organizationID) {
    this.name = name;
    this.organizationID = organizationID;
  }
}
