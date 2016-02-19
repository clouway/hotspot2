package com.clouway.anqp.adapter.http;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * Validates DTO objects coming from the client side.
 */
 class ApacheObjectValidator implements ObjectValidator {
  private final Validator validator;

  @Inject
  public ApacheObjectValidator(Validator validator){
    this.validator = validator;
  }

  @Override
  public List<Error> validate(Object dto) {
    Set<ConstraintViolation<Object>> violations = validator.validate(dto);
    List<Error> result = Lists.newArrayList();

    for (ConstraintViolation<Object> each : violations) {
      result.add(new Error(each.getMessage()));
    }

    return result;
  }
}