package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv4.Availability;
import com.clouway.anqp.IPv4AvailabilityCatalog;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 */
class IPv4Validator implements ConstraintValidator<ValidIPv4, String> {
  private final IPv4AvailabilityCatalog catalog;

  @Inject
  public IPv4Validator(IPv4AvailabilityCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void initialize(ValidIPv4 constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Optional<Availability> availability = catalog.findAvailability(value);

    if (!availability.isPresent()) {
      context.buildConstraintViolationWithTemplate("Unsupported availability  " + value + " for IPV4").addConstraintViolation();

      return false;
    }

    return true;
  }
}
