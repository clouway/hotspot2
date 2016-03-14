package com.clouway.telcong.anqp.client.emergency;

/**
 */
public class CapMessage {
  public final Object id;
  public final CapAlert alert;

  public CapMessage(Object id, CapAlert alert) {
    this.id = id;
    this.alert = alert;
  }
}
