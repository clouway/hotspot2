package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.IPv4.Availability;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.IPv4.Availability.PUBLIC;
import static com.clouway.anqp.IPv4.Availability.UNKNOWN;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 */
public class InMemoryIPv4AvailabilityCatalogTest {
  private InMemoryIPv4AvailabilityCatalog catalog = new InMemoryIPv4AvailabilityCatalog(UNKNOWN, PUBLIC);

  @Test
  public void findAll() throws Exception {
    List<Availability> wanted = Lists.newArrayList(UNKNOWN, PUBLIC);

    List<Availability> got = catalog.findAll();

    assertThat(got, deepEquals(wanted));
  }

  @Test
  public void findAvailability() throws Exception {
    Optional<Availability> result = catalog.findAvailability("UNKNOWN");

    assertThat(result.get(), deepEquals(UNKNOWN));
  }

  @Test
  public void findByUnknownName() throws Exception {
    Optional<Availability> result = catalog.findAvailability("Some");

    assertFalse(result.isPresent());
  }
}