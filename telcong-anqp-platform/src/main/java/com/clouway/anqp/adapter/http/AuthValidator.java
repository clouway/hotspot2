package com.clouway.anqp.adapter.http;

import com.clouway.anqp.AuthEntry;
import com.clouway.anqp.EapAuthCatalog;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 */
class AuthValidator implements ConstraintValidator<ValidAuth, AuthDTO> {
  private final EapAuthCatalog catalog;

  @Inject
  public AuthValidator(EapAuthCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void initialize(ValidAuth constraintAnnotation) {

  }

  @Override
  public boolean isValid(AuthDTO dto, ConstraintValidatorContext context) {
    Optional<AuthEntry> auth = catalog.findByInfo(dto.info);

    if (!auth.isPresent()) {
      context.buildConstraintViolationWithTemplate(dto.info + " is invalid Auth Info!").addConstraintViolation();
      return false;
    }

    if (!auth.get().containsType(dto.type)) {
      context.buildConstraintViolationWithTemplate(dto.type + " type is not valid for " + dto.info + " Auth Info!").addConstraintViolation();
      return false;
    }

    return true;
  }
}
