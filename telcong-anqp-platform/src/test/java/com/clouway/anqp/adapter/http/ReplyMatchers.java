package com.clouway.anqp.adapter.http;

import com.google.gson.Gson;
import com.google.sitebricks.headless.Reply;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Field;

import static javax.servlet.http.HttpServletResponse.*;
import static org.junit.Assert.fail;

/**
 */
public class ReplyMatchers {
  private static Gson gson = new Gson();

  public static Matcher<Reply<?>> containsValue(final Object value) {
    return contains(value);
  }

  public static Matcher<Reply<?>> contains(final Object value) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      public boolean matchesSafely(Reply<?> reply) {
        String firstString = gson.toJson(value);
        String secondString = gson.toJson(property("entity", reply));

        return firstString.equals(secondString);
      }

      public void describeTo(Description description) {
        description.appendText("reply value was different from expected one");
      }
    };
  }

  public static Matcher<Reply<?>> isOk() {
    return returnCodeMatcher(SC_OK);
  }

  public static Matcher<Reply<?>> isCreated() {
    return returnCodeMatcher(SC_CREATED);
  }

  public static Matcher<Reply<?>> isAccepted() {
    return returnCodeMatcher(SC_ACCEPTED);
  }

  public static Matcher<Reply<?>> isResetContent() {
    return returnCodeMatcher(SC_RESET_CONTENT);
  }

  public static Matcher<Reply<?>> isBadRequest() {
    return returnCodeMatcher(SC_BAD_REQUEST);
  }

  public static Matcher<Reply<?>> isNotFound() {
    return returnCodeMatcher(SC_NOT_FOUND);
  }

  public static Matcher<Reply<?>> isInternalServerError() {
    return returnCodeMatcher(SC_INTERNAL_SERVER_ERROR);
  }

  public static Matcher<Reply<?>> isNoContent() {
    return returnCodeMatcher(SC_NO_CONTENT);
  }

  private static Matcher<Reply<?>> returnCodeMatcher(final int expectedCode) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      public boolean matchesSafely(Reply<?> reply) {
        Integer status = property("status", reply);
        return Integer.valueOf(expectedCode).equals(status);
      }

      public void describeTo(Description description) {
        description.appendText("status of the replay was different from expected");
      }
    };
  }


  private static <T> T property(String fieldName, Reply<?> reply) {
    Field field = null;
    try {
      field = reply.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      T actual = (T) field.get(reply);

      return actual;
    } catch (NoSuchFieldException e) {
      fail(e.getMessage());
    } catch (IllegalAccessException e) {
      fail(e.getMessage());
    } finally {
      if (field != null) {
        field.setAccessible(false);
      }
    }
    return null;
  }
}
