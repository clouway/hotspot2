package com.clouway.anqp.e2e;

import com.clouway.anqp.AnqpPlatformBootstrap;
import com.clouway.anqp.adapter.persistence.PersistentDatastoreRule;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.telcong.anqp.client.sp.NAI;
import com.clouway.telcong.anqp.client.sp.Network3GPP;
import com.clouway.telcong.anqp.client.sp.NewServiceProvider;
import com.clouway.telcong.anqp.client.sp.RoamingConsortium;
import com.clouway.telcong.anqp.client.sp.ServiceProviderClient;
import com.clouway.telcong.anqp.client.sp.ServiceProviderClientFactory;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 */
public class E2eStartUp {
  @ClassRule
  public static PersistentDatastoreRule datastoreRule = new PersistentDatastoreRule();

  @Rule
  public DatastoreCleaner cleaner = new DatastoreCleaner(datastoreRule.db());

  private final AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 7896, datastoreRule.getConnectionURL());

  private final ServiceProviderClient client = ServiceProviderClientFactory.create("http://localhost:7896");

  @Before
  public void setUp() throws Exception {
    bootstrap.start();
  }

  @After
  public void tearDown() throws Exception {
    bootstrap.stop();
  }

  @Test
  public void test1() throws Exception {

    List<Network3GPP> net = Lists.newArrayList();
    List<String> dn = Lists.newArrayList();
    List<RoamingConsortium> cn = Lists.newArrayList();
    List<NAI> na = Lists.newArrayList();

    client.create(new NewServiceProvider("name", "", net, dn, cn, na));

    List l = client.findAll();

    System.out.println();
  }

  @Test
  public void testName() throws Exception {
    List<Network3GPP> net = Lists.newArrayList();
    List<String> dn = Lists.newArrayList();
    List<RoamingConsortium> cn = Lists.newArrayList();
    List<NAI> na = Lists.newArrayList();

    client.create(new NewServiceProvider("name", "", net, dn, cn, na));

    List l = client.findAll();

    System.out.println();
  }
}
