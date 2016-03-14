package com.clouway.telcong.anqp.client.authtype;

/**
 */
public class AuthenticationTypeBuilder {
  private Integer value = 256;
  private String meaning = "meaning";

  public static AuthenticationTypeBuilder newAuthType() {
    return new AuthenticationTypeBuilder();
  }

  public AuthenticationTypeBuilder value(Integer value) {
    this.value = value;
    return this;
  }

  public AuthenticationTypeBuilder meaning(String meaning) {
    this.meaning = meaning;
    return this;
  }

  public AuthenticationType build() {
    return new AuthenticationType(value, meaning);
  }
}
