package com.clouway.anqp.adapter.http;

import com.clouway.anqp.AuthEntry;
import com.clouway.anqp.EapAuthCatalog;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Type.SIM_1;
import static org.junit.Assert.*;

/**
 */
public class AuthValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private EapAuthCatalog catalog = context.mock(EapAuthCatalog.class);
  private ConstraintValidatorContext constraintValidatorContext = context.mock(ConstraintValidatorContext.class);

  private AuthValidator validator = new AuthValidator(catalog);

  @Test
  public void happyPath() throws Exception {
    AuthDTO dto = new AuthDTO("CREDENTIAL_TYPE", "SIM_1");
    final AuthEntry auth = new AuthEntry(CREDENTIAL_TYPE, SIM_1);

    context.checking(new Expectations() {{
      oneOf(catalog).findByInfo("CREDENTIAL_TYPE");
      will(returnValue(Optional.of(auth)));
    }});

    boolean valid = validator.isValid(dto, constraintValidatorContext);

    assertTrue(valid);
  }

  @Test
  public void unknownInfo() throws Exception {
    AuthDTO dto = new AuthDTO("_TYPE", "EXPANDED_EAP_METHOD_SUBFIELD");

    context.checking(new Expectations() {{
      oneOf(catalog).findByInfo("_TYPE");
      will(returnValue(Optional.absent()));

      oneOf(constraintValidatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean valid = validator.isValid(dto, constraintValidatorContext);

    assertFalse(valid);
  }

  @Test
  public void unknownType() throws Exception {
    AuthDTO dto = new AuthDTO("CREDENTIAL_TYPE", "EXPANDED_EAP_METHOD_SUBFIELD");
    final AuthEntry auth = new AuthEntry(CREDENTIAL_TYPE, SIM_1);

    context.checking(new Expectations() {{
      oneOf(catalog).findByInfo("CREDENTIAL_TYPE");
      will(returnValue(Optional.of(auth)));

      oneOf(constraintValidatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean valid = validator.isValid(dto, constraintValidatorContext);

    assertFalse(valid);
  }
}