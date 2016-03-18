package com.clouway.anqp.e2e;

import com.clouway.anqp.AnqpPlatformBootstrap;
import com.clouway.telcong.anqp.client.sp.ServiceProviderClient;
import com.clouway.telcong.anqp.client.sp.ServiceProviderClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 */
public class E2eStartUp {
  private final AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 7896, "dev.telcong.com");

  @Before
  public void setUp() throws Exception {
    bootstrap.start();
  }

  @After
  public void tearDown() throws Exception {
    bootstrap.stop();
  }

  @Test
  public void testName() throws Exception {
    ServiceProviderClient client = ServiceProviderClientFactory.create("http://localhost:7896");

    List l = client.findAll();

    System.out.println();
  }
}
