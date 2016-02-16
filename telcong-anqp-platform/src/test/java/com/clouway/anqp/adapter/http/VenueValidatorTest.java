package com.clouway.anqp.adapter.http;

import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

/**
 */
public class VenueValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private VenueFinder finder = context.mock(VenueFinder.class);
  private ConstraintValidatorContext constraintValidatorContext = context.mock(ConstraintValidatorContext.class);

  private VenueValidator validator = new VenueValidator(finder);

  @Test
  public void happyPath() throws Exception {
    final VenueDTO dto = new VenueDTO("assembly", "arena", Lists.newArrayList(new VenueNameDTO("aaa", "bbb")));
    final VenueGroup group = new VenueGroup(dto.group);
    final VenueTypeList type = new VenueTypeList(new VenueType("arena"), new VenueType("other"));

    context.checking(new Expectations() {{
      oneOf(finder).findTypesBy(group);
      will(returnValue(Optional.of(type)));
    }});

    boolean result = validator.isValid(dto, constraintValidatorContext);

    assertTrue(result);
  }

  @Test
  public void undefinedVenueGroup() throws Exception {
    final VenueDTO dto = new VenueDTO("xxxx", "arena", Lists.newArrayList(new VenueNameDTO("aaa", "aaaa")));
    final VenueGroup group = new VenueGroup(dto.group);

    context.checking(new Expectations() {{
      oneOf(finder).findTypesBy(group);
      will(returnValue(Optional.absent()));

      oneOf(constraintValidatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean result = validator.isValid(dto, constraintValidatorContext);

    assertFalse(result);
  }

  @Test
  public void undefinedVenueType() throws Exception {
    final VenueDTO dto = new VenueDTO("group", "zxc", Lists.newArrayList(new VenueNameDTO("aaa", "aaaa")));
    final VenueGroup group = new VenueGroup(dto.group);
    final VenueTypeList type = new VenueTypeList(new VenueType("type"));

    context.checking(new Expectations() {{
      oneOf(finder).findTypesBy(group);
      will(returnValue(Optional.of(type)));

      oneOf(constraintValidatorContext).buildConstraintViolationWithTemplate(with(any(String.class)));
    }});

    boolean result = validator.isValid(dto, constraintValidatorContext);

    assertFalse(result);
  }
}