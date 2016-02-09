package com.clouway.anqp.core;

/**
 * ProgrammerMistake is a an exceptional class which signals an occurrence of something that ought not to happen.
 */
public class ProgrammerMistake extends RuntimeException {

  /**
   * Creates a new {@link ProgrammerMistake} by providing the error message to be
   * displayed.
   *
   * @param msg the msg to be displayed.
   */
  public ProgrammerMistake(String msg) {
    super(msg);
  }

  /**
   * Wraps any {@link Throwable} as ProgrammerMistake.
   *
   * @param e the exception to be wrapped
   */
  public ProgrammerMistake(Throwable e) {
    super(e);
  }

}