package com.clouway.telcong.anqp.client.serviceprovider;

import java.util.List;

/**
 */
public class EAP {
  public final String method;
  public final List<Auth> authentications;

  public EAP(String method, List<Auth> authentications) {
    this.method = method;
    this.authentications = authentications;
  }
}
