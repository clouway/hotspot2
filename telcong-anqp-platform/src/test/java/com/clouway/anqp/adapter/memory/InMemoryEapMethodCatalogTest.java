package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.EAP.Method;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.EAP.Method.EAP_SIM;
import static com.clouway.anqp.EAP.Method.PEAP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class InMemoryEapMethodCatalogTest {
  private InMemoryEapMethodCatalog catalog = new InMemoryEapMethodCatalog(PEAP, EAP_SIM);

  @Test
  public void findAll() throws Exception {
    List<Method> want = Lists.newArrayList(PEAP, EAP_SIM);

    List<Method> got = catalog.findAll();

    assertThat(got, is(want));
  }
}