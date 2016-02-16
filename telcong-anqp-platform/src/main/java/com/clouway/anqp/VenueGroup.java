package com.clouway.anqp;

/**
 * Venue Group—The venue category that this access point belongs to. The following options are available:Unspecified,
 * Assembly, Business, Educational,Factory and Industrial , Institutional, Mercantile, Residential, Storage,
 * Utility and Misc ,Vehicular and Outdoor.
 */
public final class VenueGroup {
  public final String name;

  public VenueGroup(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VenueGroup group = (VenueGroup) o;

    return !(name != null ? !name.equals(group.name) : group.name != null);

  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}