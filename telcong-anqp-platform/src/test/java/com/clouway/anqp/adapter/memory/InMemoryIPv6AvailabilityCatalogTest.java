package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.IPv6.Availability;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.IPv6.Availability.AVAILABLE;
import static com.clouway.anqp.IPv6.Availability.NOT_AVAILABLE;
import static com.clouway.anqp.IPv6.Availability.UNKNOWN;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 */
public class InMemoryIPv6AvailabilityCatalogTest {
  private InMemoryIPv6AvailabilityCatalog catalog = new InMemoryIPv6AvailabilityCatalog(UNKNOWN, AVAILABLE);

  @Test
  public void findAll() throws Exception {
    List<Availability> wanted = Lists.newArrayList(UNKNOWN, AVAILABLE);

    List<Availability> got = catalog.findAll();

    assertThat(got, deepEquals(wanted));
  }


  @Test
  public void findAvailability() throws Exception {
    Optional<Availability> result = catalog.findByAvailability("UNKNOWN");

    assertThat(result.get(), deepEquals(UNKNOWN));
  }

  @Test
  public void findByUnknownName() throws Exception {
    Optional<Availability> result = catalog.findByAvailability("NOT_AVAILABLE_2");

    assertFalse(result.isPresent());
  }
}