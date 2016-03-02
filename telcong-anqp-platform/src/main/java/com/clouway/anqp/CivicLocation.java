package com.clouway.anqp;

/**
 * This class contains information for civic location of access point.
 */
public class CivicLocation {
  public final String country;
  public final String city;
  public final String street;
  public final String streetNumber;
  public final String postCode;

  public CivicLocation(String country, String city, String street, String streetNumber, String postCode) {
    this.country = country;
    this.city = city;
    this.street = street;
    this.streetNumber = streetNumber;
    this.postCode = postCode;
  }
}
