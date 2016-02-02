package com.clouway.anqp.util.matchers;

import org.hamcrest.Matcher;

/**
 */
public class EqualityMatchers {

  public static <T> Matcher<T> matching(T item) {
    return new GsonCallMatcher<>(item);
  }

  public static <T> Matcher<T> deepEquals(final T item) {
    return new GsonEqualsMatcher<>(item);
  }

}
