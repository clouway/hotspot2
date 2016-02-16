package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IpTypeCatalog;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static com.clouway.anqp.IpType.NOT_AVAILABLE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IpTypeValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final ConstraintValidatorContext validatorContext = context.mock(ConstraintValidatorContext.class);
  private final IpTypeCatalog catalog = context.mock(IpTypeCatalog.class);
  private final IpTypeValidator validator = new IpTypeValidator(catalog);

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).isSupported("NOT_AVAILABLE");
      will(returnValue(true));

      never(validatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean isValid = validator.isValid("NOT_AVAILABLE", validatorContext);

    assertTrue(isValid);
  }

  @Test
  public void unsupportedType() throws Exception {
    context.checking(new Expectations() {{
      oneOf(catalog).isSupported("SOMETHING_OTHER");
      will(returnValue(false));

      oneOf(validatorContext).getDefaultConstraintMessageTemplate();
      will(returnValue("unsupported operation"));

      oneOf(validatorContext).buildConstraintViolationWithTemplate(with("unsupported operation"));
    }});

    boolean isValid = validator.isValid("SOMETHING_OTHER", validatorContext);

    assertFalse(isValid);

  }
}