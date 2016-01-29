package com.clouway.anqp.snmp;

/**
 * Community object is representing the SNMP community which is used for communication with the external SNMP host.
 */
public final class Community {
  public final String read;
  public final String write;

  public Community(String write, String read) {
    this.write = write;
    this.read = read;
  }
}
