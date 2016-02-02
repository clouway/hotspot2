package com.clouway.anqp.util.matchers;

import com.google.gson.Gson;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

/**
 */
class GsonCallMatcher<T> extends DiagnosingMatcher<T> {
  private final T item;
  private final Gson gson = new Gson();

  GsonCallMatcher(T item) {
    this.item = item;
  }

  @Override
  protected boolean matches(Object o, Description mismatch) {
    boolean equals = deepEquals(item, o);

    if (!equals) {
      mismatch.appendText("it was " + gson.toJson(o));
    }

    return equals;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText(gson.toJson(item));
  }

  private boolean deepEquals(Object obj1, Object obj2) {
    String json1 = gson.toJson(obj1);
    String json2 = gson.toJson(obj2);

    return json1.equals(json2);
  }
}
