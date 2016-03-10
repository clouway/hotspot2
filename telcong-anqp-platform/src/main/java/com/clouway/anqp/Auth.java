package com.clouway.anqp;

/**
 * Use of multiple Authentication Parameter subfields allows all the required authentication
 * parameter requirements to be provided.
 */
public class Auth {
  public enum Info {
    EXPANDED_EAP_METHOD, NON_EAP_INNER_AUTHENTICATION_TYPE, INNER_AUTHENTICATION_EAP_METHOD_TYPE,
    EXPANDED_INNER_EAP_METHOD, CREDENTIAL_TYPE, TUNNELED_EAP_METHOD_CREDENTIAL_TYPE
  }

  public enum Type {
    RESERVED_0, PAP_1, CHAP_2, MSCHAP_3, MSCHAPV2_4,
    SIM_1, USIM_2, NFC_SECURE_ELEMENT_3, HARDWARE_TOKEN_4, SOFTOKEN_5, CERTIFICATE_6, USERNAME_PASSWORD_7,
    NONE_8, RESERVED_8, RESERVED_9, ANONYMOUS_9, VENDOR_SPECIFIC_10, EXPANDED_EAP_METHOD_SUBFIELD, EAP_TLS, EAP_SIM, EAP_TTLS, EAP_AKA
  }

  public final Info info;
  public final Type type;

  public Auth(Info info, Type type) {
    this.info = info;
    this.type = type;
  }
}
