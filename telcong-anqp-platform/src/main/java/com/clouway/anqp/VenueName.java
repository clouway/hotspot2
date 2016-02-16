package com.clouway.anqp;

/**
 * {@link com.clouway.anqp.VenueName} may be used to provide additional metadata on the BSS.
 */
public class VenueName {
  public final String name;
  public final Language language;

  public VenueName(String name, Language language) {
    this.name = name;
    this.language = language;
  }

  public static VenueName defaultName() {
    return new VenueName("There are no Venue Names defined.", new Language("en"));
  }
}
