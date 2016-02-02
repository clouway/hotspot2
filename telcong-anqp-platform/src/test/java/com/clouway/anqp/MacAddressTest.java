package com.clouway.anqp;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 */
public class MacAddressTest {
  @Test
  public void happyPath() throws Exception {
    MacAddress mac = new MacAddress("aa:bb:cc:dd:ee:ff");

    assertThat(mac.value, is("aa:bb:cc:dd:ee:ff"));
  }

  @Test
  public void dotSeparated() throws Exception {
    MacAddress mac = new MacAddress("aab.bcc.dde.eff");

    assertThat(mac.value, is("aa:bb:cc:dd:ee:ff"));
  }

  @Test
  public void commaSeparated() throws Exception {
    MacAddress mac = new MacAddress("aab,bcc,dde,eff");

    assertThat(mac.value, is("aa:bb:cc:dd:ee:ff"));
  }

  @Test
  public void dashSeparated() throws Exception {
    MacAddress mac = new MacAddress("aa-bb-cc-dd-ee-ff");

    assertThat(mac.value, is("aa:bb:cc:dd:ee:ff"));
  }

  @Test
  public void upperCase() throws Exception {
    MacAddress mac = new MacAddress("AA:BB:CC:DD:EE:FF");

    assertThat(mac.value, is("aa:bb:cc:dd:ee:ff"));
  }
}