package com.clouway.anqp;

/**
 * Specify a venue code to be advertised in the IEs from APs associated with this hotspot profile.
 */
public final class VenueType {
  public final String name;

  public VenueType(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VenueType venueType = (VenueType) o;

    return !(name != null ? !name.equals(venueType.name) : venueType.name != null);

  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}