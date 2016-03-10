package com.clouway.telcong.anqp.client.util.matchers;

import com.google.gson.Gson;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 */
class GsonEqualsMatcher<T> extends TypeSafeMatcher<T> {

  private final T item;
  private final Gson gson = new Gson();

  GsonEqualsMatcher(T item) {
    this.item = item;
  }

  @Override
  protected boolean matchesSafely(T item) {
    final String firstString = gson.toJson(this.item);
    final String secondString = gson.toJson(item);

    return firstString.equals(secondString);
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("expected <" + gson.toJson(item) + ">");
  }
}
