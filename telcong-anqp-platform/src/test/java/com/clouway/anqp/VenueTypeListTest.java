package com.clouway.anqp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 */
public class VenueTypeListTest {

  @Test
  public void contains() throws Exception {
    VenueType type1 = new VenueType("A");
    VenueType type2 = new VenueType("B");

    VenueTypeList list = new VenueTypeList(type1, type2);

    assertTrue(list.contains(type1));
  }

  @Test
  public void containsWithUnknownType() throws Exception {
    VenueType type1 = new VenueType("A");
    VenueType type2 = new VenueType("B");

    VenueTypeList list = new VenueTypeList(type1);

    assertFalse(list.contains(type2));
  }
}