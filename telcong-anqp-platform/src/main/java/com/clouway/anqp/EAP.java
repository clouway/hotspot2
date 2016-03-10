package com.clouway.anqp;

/**
 * Each NAI realm is a list of one or more EAP Method subfields, which that NAI realm uses for authentication.
 */
public class EAP {
  /**
   * Select one of the options below to identify the EAP authentication method supported by the hotspot realm.
   * <p/>
   * -	crypto-card: Crypto card authentication
   * -	eap-aka: EAP for Universal Mobile Telecommunications System (UMTS) Authentication and Key Agreement
   * -	eap-sim: EAP for GSM Subscriber Identity Modules
   * -	eap-tls: EAP-Transport Layer Security
   * -	eap-ttls: EAP-Tunneled Transport Layer Security
   * -	generic-token-card: EAP Generic Token Card (EAP-GTC)
   * -	identity: EAP Identity method
   * -	notification: The hotspot realm uses EAP Notification messages for authentication.
   * -	one-time-password: Authentication with a single-use password
   * -	peap: Protected Extensible Authentication Protocol
   * -	peap-mschapv2: Protected Extensible Authentication Protocol with Microsoft Challenge Handshake Authentication Protocol version 2
   */
  public enum Method {
    CRYPTO_CARD,
    EAP_AKA,
    EAP_SIM,
    EAP_TLS,
    EAP_TTLS,
    GENERIC_TOKEN_CARD,
    IDENTITY,
    NOTIFICATION,
    ONE_TIME_PASSWORD,
    PEAP,
    PEAP_MSCHAPV2
  }

  public final Method method;

  public EAP(Method method) {
    this.method = method;
  }
}
