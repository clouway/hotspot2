package com.clouway.telcong.anqp.client.apcontroller;

/**
 */
public class NewApControllerBuilder {
  private String ip = "ip";
  private String mac = "aa:bb:cc";
  private String serialNumber = "sn";
  private String model = "model";

  public static NewApControllerBuilder newApController() {
    return new NewApControllerBuilder();
  }

  public NewApControllerBuilder ip(String ip) {
    this.ip = ip;
    return this;
  }

  public NewApControllerBuilder mac(String mac) {
    this.mac = mac;
    return this;
  }

  public NewApControllerBuilder serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  public NewApControllerBuilder model(String model) {
    this.model = model;
    return this;
  }

  public NewApController build() {
    return new NewApController(ip, mac, serialNumber, model);
  }
}
