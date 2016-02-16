package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.IpTypeCatalog;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IpTypeCatalogTest {
  private final IpTypeCatalog catalog = new InMemoryIpTypeCatalog(new LinkedHashMap<String,Integer>() {{
    put("PUBLIC", 1);
    put("PORT_RESTRICTED", 2);
  }});

  @Test
  public void findType() throws Exception {
    Integer result = catalog.findId("PUBLIC").get();

    assertThat(result, is(1));
  }


  @Test
  public void missingType() throws Exception {
    Optional<Integer> result = catalog.findId("SINGLE_NAT_PRIVATE");

    assertFalse(result.isPresent());
  }

  @Test
  public void supportedType() throws Exception {
    boolean isSupported = catalog.isSupported("PUBLIC");

    assertTrue(isSupported);
  }

  @Test
  public void notSupportedType() throws Exception {
    boolean isSupported = catalog.isSupported("PORT_RESTRICTED_AND_DOUBLE_NAT");

    assertFalse(isSupported);
  }

  @Test
  public void getAll() throws Exception {
    List<String> actual = catalog.getAll();
    List<String> expected = Lists.newArrayList("PUBLIC", "PORT_RESTRICTED");

    assertThat(actual, deepEquals(expected));
  }
}