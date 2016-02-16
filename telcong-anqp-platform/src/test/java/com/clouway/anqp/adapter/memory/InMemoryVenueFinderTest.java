package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueItem;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.VenueItemBuilder.newVenueItemBuilder;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

/**
 */
public class InMemoryVenueFinderTest {
  @Test
  public void findAll() throws Exception {
    VenueItem item = newVenueItemBuilder().group("assembly").types(new VenueType("arena"), new VenueType("bar")).build();

    InMemoryVenueFinder finder = new InMemoryVenueFinder(item);

    List<VenueItem> actual = finder.findAll();

    List<VenueItem> items = Lists.newArrayList(item);

    assertThat(actual, deepEquals(items));
  }

  @Test
  public void findTypesByGroup() throws Exception {
    VenueItem item = newVenueItemBuilder().group("assembly").types(new VenueType("arena"), new VenueType("bar")).build();

    InMemoryVenueFinder finder = new InMemoryVenueFinder(item);

    VenueTypeList actual = finder.findTypesBy(new VenueGroup("assembly")).get();

    VenueTypeList types = new VenueTypeList(new VenueType("arena"), new VenueType("bar"));

    assertThat(actual, deepEquals(types));
  }

  @Test
  public void findTypesByUnknownGroup() throws Exception {
    VenueItem item = newVenueItemBuilder().build();

    InMemoryVenueFinder finder = new InMemoryVenueFinder(item);

    Optional<VenueTypeList> actual = finder.findTypesBy(new VenueGroup("aaa"));

    assertFalse(actual.isPresent());
  }
}