package com.clouway.anqp;

/**
 */
public class IPv6 {
  public enum Availability {
    NOT_AVAILABLE(0), AVAILABLE(1), UNKNOWN(2);

    public final int id;

    Availability(int id) {
      this.id = id;
    }
  }

  public final Availability availability;

  public IPv6(Availability availability) {
    this.availability = availability;
  }
}