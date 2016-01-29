package com.clouway.anqp.api.datastore;

/**
 *
 */
public enum Order {
  ASCENDING(1), DESCENDING(-1);

  private final int value;

  Order(int value) {
    this.value = value;
  }

  /**
   * The value that is representing the order information.
   *
   * @return value that is indicating the kind of the order
   */
  protected int getValue() {
    return value;
  }
}
