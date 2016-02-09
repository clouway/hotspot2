package com.clouway.anqp.adapter.http;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * ValidationException is an exception class which is used for indication of errors which are occurring in the system.
 * <p/>
 * The goal of the api is to simplify the creation of the error messages by using an fluent api.
 * <p/>
 * An example of firing a single error.
 * <pre>
 *  newError("my error message").fire();
 * </pre>
 */
class ValidationException extends RuntimeException {
  /**
   * Adds a new Error
   *
   * @param message the error message
   * @return the newly registered builder.
   */
  public static Builder newError(String message) {
    return new Builder(message);
  }

  /**
   * A builder class for building of errors.
   */
  public static class Builder {
    private List<Error> errors = Lists.newArrayList();

    public Builder(String message) {
      errors.add(new Error(message));
    }

    public void fire() {
      throw new ValidationException(errors);
    }
  }

  public final List<Error> errors;

  public ValidationException(List<Error> errors) {
    this.errors = errors;
  }

}