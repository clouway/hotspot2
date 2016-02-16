package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IpTypeCatalog;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class IpTypeValidator implements ConstraintValidator<IpType, String> {
  private final IpTypeCatalog catalog;

  @Inject
  public IpTypeValidator(IpTypeCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void initialize(IpType constraintAnnotation) {

  }

  @Override
  public boolean isValid(String type, ConstraintValidatorContext context) {
    boolean isValid = catalog.isSupported(type);

    if (!isValid) {
      String message = context.getDefaultConstraintMessageTemplate();

      context.buildConstraintViolationWithTemplate(message);
    }

    return isValid;
  }
}
