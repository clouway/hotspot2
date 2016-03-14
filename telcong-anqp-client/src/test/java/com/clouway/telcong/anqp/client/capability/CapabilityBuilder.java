package com.clouway.telcong.anqp.client.capability;

/**
 */
public class CapabilityBuilder {
  private Integer id = 1;
  private String name = "name";

  public static CapabilityBuilder newCapability() {
    return new CapabilityBuilder();
  }

  public CapabilityBuilder id(Integer id) {
    this.id = id;
    return this;
  }

  public CapabilityBuilder name(String name) {
    this.name = name;
    return this;
  }

  public Capability build() {
    return new Capability(id, name);
  }
}
