package com.clouway.anqp.api.datastore;

import org.junit.Test;

import static com.clouway.anqp.api.datastore.Filter.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 */
public class FilterEqualityTest {

  @Test
  public void equalityIsChecking() {
    assertThat(where("test").is("abc"), is(equalTo(where("test").is("abc"))));
    assertThat(where("test").is("abc"), is(not(equalTo(where("test").is("anotherbac")))));
  }

  @Test
  public void equalityIsCheckingAndOrdering() {
    assertThat(where("test").is("abc").order("test", Order.ASCENDING), is(equalTo(where("test").is("abc").order("test", Order.ASCENDING))));
    assertThat(where("test").is("abc").order("test", Order.DESCENDING), is(not(equalTo(where("test").is("abc").order("test", Order.ASCENDING)))));
  }
}
