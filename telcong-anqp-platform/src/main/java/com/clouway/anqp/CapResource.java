package com.clouway.anqp;

/**
 * The resource segment provides an optional reference to additional information related to the info segment
 */
public final class CapResource {
  public final String content;

  public CapResource(String content) {
    this.content = content;
  }
}
