package com.clouway.anqp.adapter.persistence;

/**
 */
class CivicLocationEntity {
  String country;
  String city;
  String street;
  String streetNumber;
  String postCode;

  public CivicLocationEntity() {
  }

  CivicLocationEntity(String country, String city, String street, String streetNumber, String postCode) {
    this.country = country;
    this.city = city;
    this.street = street;
    this.streetNumber = streetNumber;
    this.postCode = postCode;
  }
}
