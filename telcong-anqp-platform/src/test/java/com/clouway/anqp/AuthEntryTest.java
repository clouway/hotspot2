package com.clouway.anqp;

import com.clouway.anqp.Auth.Type;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Type.CHAP_2;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.junit.Assert.*;

/**
 */
public class AuthEntryTest {
  private AuthEntry authEntry = new AuthEntry(CREDENTIAL_TYPE, CHAP_2);

  @Test
  public void getTypes() throws Exception {
    List<Type> want = Lists.newArrayList(CHAP_2);

    List<Type> got = authEntry.getTypes();

    assertThat(got, deepEquals(want));
  }

  @Test
  public void containsType() throws Exception {
    boolean result = authEntry.containsType("CHAP_2");

    assertTrue(result);
  }

  @Test
  public void containsUnknownType() throws Exception {
    boolean result = authEntry.containsType("CHAP_42");

    assertFalse(result);
  }
}