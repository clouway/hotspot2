package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv4AvailabilityCatalog;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static com.clouway.anqp.IPv4.Availability.PUBLIC;
import static org.junit.Assert.*;

/**
 */
public class IPv4ValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private IPv4AvailabilityCatalog catalog = context.mock(IPv4AvailabilityCatalog.class);
  private ConstraintValidatorContext validatorContext = context.mock(ConstraintValidatorContext.class);

  private IPv4Validator validator = new IPv4Validator(catalog);

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).findAvailability("PUBLIC");
      will(returnValue(Optional.of(PUBLIC)));
    }});

    boolean result = validator.isValid("PUBLIC", validatorContext);

    assertTrue(result);
  }

  @Test
  public void unknownAvailability() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).findAvailability("SINGLE_NAT_PRIVATE");
      will(returnValue(Optional.absent()));

      oneOf(validatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean result = validator.isValid("SINGLE_NAT_PRIVATE", validatorContext);

    assertFalse(result);
  }
}