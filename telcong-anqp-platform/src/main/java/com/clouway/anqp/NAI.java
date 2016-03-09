package com.clouway.anqp;

/**
 * The NAI Realm list provides a list of network access identifier (NAI) realms corresponding to SSPs or other
 * entities whose networks or services are accessible via this AP; optionally included for each NAI realm is a
 * list of one or more EAP Method subfields, which that NAI realm uses for authentication.
 */
public class NAI {
  public final String name;

  public NAI(String name) {
    this.name = name;
  }
}
