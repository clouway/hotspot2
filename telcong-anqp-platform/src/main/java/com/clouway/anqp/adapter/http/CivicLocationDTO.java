package com.clouway.anqp.adapter.http;

/**
 */
class CivicLocationDTO {
  final String country;
  final String city;
  final String street;
  final String streetNumber;
  final String postCode;

  CivicLocationDTO(String country, String city, String street, String streetNumber, String postCode) {
    this.country = country;
    this.city = city;
    this.street = street;
    this.streetNumber = streetNumber;
    this.postCode = postCode;
  }
}
