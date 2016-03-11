package com.clouway.anqp;


public final class CapMessage {
  public final ID id;
  public final CapAlert alert;

  public CapMessage(ID id, CapAlert alert) {
    this.id = id;
    this.alert = alert;
  }
}
