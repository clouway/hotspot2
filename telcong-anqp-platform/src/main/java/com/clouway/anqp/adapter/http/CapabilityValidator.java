package com.clouway.anqp.adapter.http;

import com.clouway.anqp.CapabilityCatalog;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

class CapabilityValidator implements ConstraintValidator<Capability, List<Integer>> {
  private final CapabilityCatalog catalog;

  @Inject
  public CapabilityValidator(CapabilityCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void initialize(Capability constraintAnnotation) {

  }

  @Override
  public boolean isValid(List<Integer> ids, ConstraintValidatorContext context) {
    if (ids == null || ids.isEmpty()) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("capabilities cannot be empty").addConstraintViolation();

      return false;
    }

    for (Integer id : ids) {
      if (!catalog.isSupported(id)) {
        String message = context.getDefaultConstraintMessageTemplate();

        context.buildConstraintViolationWithTemplate(message);

        return false;
      }
    }

    return true;
  }
}
