package com.clouway.anqp.adapter.http;

import com.clouway.anqp.CapabilityCatalog;
import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CapabilityValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final ConstraintValidatorContext constraintValidator = context.mock(ConstraintValidatorContext.class);
  private final CapabilityCatalog catalog = context.mock(CapabilityCatalog.class);
  private final CapabilityValidator validator = new CapabilityValidator(catalog);

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).isSupported(256);
      will(returnValue(true));

      never(constraintValidator).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    List<Integer> ids = Lists.newArrayList(256);

    boolean isValid = validator.isValid(ids, constraintValidator);

    assertTrue(isValid);
  }

  @Test
  public void notSupported() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).isSupported(255555);
      will(returnValue(false));

      oneOf(constraintValidator).getDefaultConstraintMessageTemplate();
      will(returnValue("Found capability id that is not supported"));

      oneOf(constraintValidator).buildConstraintViolationWithTemplate("Found capability id that is not supported");
    }});

    List<Integer> ids = Lists.newArrayList(255555);

    boolean isValid = validator.isValid(ids, constraintValidator);

    assertFalse(isValid);
  }

  @Test
  public void emptyList() throws Exception {
    context.checking(new Expectations() {{
      oneOf(constraintValidator).disableDefaultConstraintViolation();

      oneOf(constraintValidator).buildConstraintViolationWithTemplate("capabilities cannot be empty");
    }});

    boolean isValid = validator.isValid(Lists.<Integer>newArrayList(), constraintValidator);

    assertFalse(isValid);
  }

  @Test
  public void nullableList() throws Exception {
    context.checking(new Expectations() {{
      oneOf(constraintValidator).disableDefaultConstraintViolation();

      oneOf(constraintValidator).buildConstraintViolationWithTemplate("capabilities cannot be empty");
    }});

    boolean isValid = validator.isValid(null, constraintValidator);

    assertFalse(isValid);
  }
}