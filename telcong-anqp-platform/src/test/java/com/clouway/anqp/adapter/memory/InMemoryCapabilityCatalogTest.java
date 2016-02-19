package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.Capability;
import com.clouway.anqp.CapabilityCatalog;
import com.clouway.anqp.CapabilityList;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class InMemoryCapabilityCatalogTest {
  private CapabilityCatalog catalog = new InMemoryCapabilityCatalog(new LinkedHashMap<Integer, Capability>() {{
    put(1, new Capability(1, "A"));
    put(2, new Capability(2, "B"));
  }});

  @Test
  public void findAll() throws Exception {
    CapabilityList actual = catalog.findAll();
    CapabilityList expected = new CapabilityList(Lists.newArrayList(new Capability(1, "A"), new Capability(2, "B")));

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void findById() throws Exception {
    Capability actual = catalog.findById(2).get();

    Capability expected = new Capability(2, "B");

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<Capability> found = catalog.findById(10);

    assertFalse(found.isPresent());
  }

  @Test
  public void findOrderedCapabilitiesByIds() throws Exception {
    CapabilityList actual = catalog.findByIds(Lists.newArrayList(2, 1));

    List<Capability> capabilities = Lists.newArrayList(new Capability(1, "A"), new Capability(2, "B"));

    CapabilityList expected = new CapabilityList(capabilities);

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void findCapabilitiesByEmptyIds() throws Exception {
    CapabilityList capabilities = catalog.findByIds(Lists.<Integer>newArrayList());

    assertTrue(capabilities.values.isEmpty());
  }

  @Test
  public void findAvailableCapabilitiesByIds() throws Exception {
    CapabilityList actual = catalog.findByIds(Lists.newArrayList(2, 15));

    List<Capability> capabilities = Lists.newArrayList(new Capability(2, "B"));

    CapabilityList expected = new CapabilityList(capabilities);

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void supportedCapability() throws Exception {
    assertTrue(catalog.isSupported(1));
  }

  @Test
  public void unsupportedCapability() throws Exception {
    assertFalse(catalog.isSupported(25));
  }
}