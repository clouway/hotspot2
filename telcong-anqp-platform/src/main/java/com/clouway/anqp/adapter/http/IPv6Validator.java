package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv6.Availability;
import com.clouway.anqp.IPv6AvailabilityCatalog;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 */
class IPv6Validator implements ConstraintValidator<ValidIPv6, String> {
  private final IPv6AvailabilityCatalog catalog;

  @Inject
  public IPv6Validator(IPv6AvailabilityCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void initialize(ValidIPv6 constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Optional<Availability> availability = catalog.findByAvailability(value);

    if (!availability.isPresent()) {
      context.buildConstraintViolationWithTemplate("Unsupported availability  " + value + " for IPV6").addConstraintViolation();

      return false;
    }
    return true;
  }
}
