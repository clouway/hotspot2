package com.clouway.anqp.adapter.http;

import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 */
class VenueValidator implements ConstraintValidator<ValidVenue, VenueDTO> {
  private final VenueFinder venueFinder;

  @Inject
  VenueValidator(VenueFinder venueFinder) {
    this.venueFinder = venueFinder;
  }

  @Override
  public void initialize(ValidVenue constraintAnnotation) {

  }

  @Override
  public boolean isValid(VenueDTO value, ConstraintValidatorContext context) {
    VenueGroup group = new VenueGroup(value.group);
    VenueType type = new VenueType(value.type);

    Optional<VenueTypeList> types = venueFinder.findTypesBy(group);

    if (!types.isPresent()) {
      context.buildConstraintViolationWithTemplate(value.group + " group is not valid Venue Group!").addConstraintViolation();
      return false;
    }

    if (!types.get().contains(type)) {
      context.buildConstraintViolationWithTemplate(value.type + " type is not valid for " + value.group + " Venue Group!").addConstraintViolation();
      return false;
    }

    return true;
  }
}