package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv6AvailabilityCatalog;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static com.clouway.anqp.IPv6.Availability.AVAILABLE;
import static org.junit.Assert.*;

/**
 */
public class IPv6ValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private IPv6AvailabilityCatalog catalog = context.mock(IPv6AvailabilityCatalog.class);
  private final ConstraintValidatorContext validatorContext = context.mock(ConstraintValidatorContext.class);

  private IPv6Validator validator = new IPv6Validator(catalog);

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).findByAvailability("AVAILABLE");
      will(returnValue(Optional.of(AVAILABLE)));
    }});

    boolean result = validator.isValid("AVAILABLE", validatorContext);

    assertTrue(result);
  }

  @Test
  public void unknownAvailability() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).findByAvailability("availability_field");
      will(returnValue(Optional.absent()));

      oneOf(validatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean result = validator.isValid("availability_field", validatorContext);

    assertFalse(result);
  }
}