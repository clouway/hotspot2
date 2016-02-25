package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.AuthenticationType;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 */
public class InMemoryAuthenticationTypeFinderTest {

  @Test
  public void happyPath() throws Exception {
    AuthenticationType type1 = new AuthenticationType(1, "one");
    AuthenticationType type2 = new AuthenticationType(2, "two");

    InMemoryAuthenticationTypeFinder finder = new InMemoryAuthenticationTypeFinder(type1, type2);

    List<AuthenticationType> types = finder.findAll();

    List<AuthenticationType> expected = Lists.newArrayList(type1, type2);

    assertThat(types, is(expected));
  }
}