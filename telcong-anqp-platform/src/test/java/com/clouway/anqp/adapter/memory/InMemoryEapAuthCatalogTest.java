package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.AuthEntry;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Type.SIM_1;
import static com.clouway.anqp.Auth.Type.USIM_2;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

/**
 */
public class InMemoryEapAuthCatalogTest {
  private final AuthEntry authItem = new AuthEntry(CREDENTIAL_TYPE, SIM_1, USIM_2);
  private InMemoryEapAuthCatalog catalog = new InMemoryEapAuthCatalog(authItem);

  @Test
  public void findAll() throws Exception {
    List<AuthEntry> want = Lists.newArrayList(new AuthEntry(CREDENTIAL_TYPE, SIM_1, USIM_2));

    List<AuthEntry> items = catalog.findAll();

    assertThat(items, deepEquals(want));
  }

  @Test
  public void findByInfo() throws Exception {
    AuthEntry got = catalog.findByInfo("CREDENTIAL_TYPE").get();

    assertThat(got, deepEquals(authItem));
  }

  @Test
  public void findUnknownByInfo() throws Exception {
    Optional<AuthEntry> got = catalog.findByInfo("CREDENTIAL_");

    assertFalse(got.isPresent());
  }
}