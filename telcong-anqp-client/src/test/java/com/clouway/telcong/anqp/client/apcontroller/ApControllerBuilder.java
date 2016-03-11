package com.clouway.telcong.anqp.client.apcontroller;

/**
 */
public class ApControllerBuilder {
  private Object id = "id";
  private String ip = "ip";
  private String mac = "aa:bb:cc";
  private String serialNumber = "sn";
  private String model = "model";

  public static ApControllerBuilder apController() {
    return new ApControllerBuilder();
  }

  public ApControllerBuilder id(String id) {
    this.id = id;
    return this;
  }

  public ApControllerBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public ApControllerBuilder mac(String mac) {
    this.mac = mac;
    return this;
  }

  public ApControllerBuilder serialNumber(String sn) {
    this.serialNumber = sn;
    return this;
  }

  public ApControllerBuilder model(String model) {
    this.model = model;
    return this;
  }

  public ApController build() {
    return new ApController(id, ip, mac, serialNumber, model);
  }
}
