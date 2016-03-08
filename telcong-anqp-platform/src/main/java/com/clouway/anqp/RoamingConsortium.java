package com.clouway.anqp;

/**
 * The Roaming Consortium provides a list of information about the Roaming Consortium and/or SSPs
 * whose networks are accessible via this AP.
 */
public class RoamingConsortium {
  public final String name;
  public final String organizationID;

  public RoamingConsortium(String name, String  organizationID) {
    this.name = name;
    this.organizationID = organizationID;
  }
}
