package com.clouway.anqp;

/**
 */
public final class ID {
  public final Object value;

  public ID(Object value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ID)) return false;

    ID id = (ID) o;

    return !(value != null ? !value.equals(id.value) : id.value != null);

  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
