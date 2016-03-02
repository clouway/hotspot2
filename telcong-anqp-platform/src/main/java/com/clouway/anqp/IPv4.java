package com.clouway.anqp;

/**
 */
public class IPv4 {
  public enum Availability {
    NOT_AVAILABLE(0), PUBLIC(1), PORT_RESTRICTED(2), SINGLE_NAT_PRIVATE(3), UNKNOWN(7);

    public final int id;

    Availability(int id) {
      this.id = id;
    }
  }

  public final Availability availability;

  public IPv4(Availability availability) {
    this.availability = availability;
  }
}
