package com.clouway.anqp.e2e;

import com.clouway.anqp.AnqpPlatformBootstrap;
import com.clouway.anqp.adapter.persistence.PersistentDatastoreRule;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.sp.*;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 */
public class ServiceProviderEndToEndTest {
  @ClassRule
  public static PersistentDatastoreRule datastoreRule = new PersistentDatastoreRule();

  @Rule
  public DatastoreCleaner cleaner = new DatastoreCleaner(datastoreRule.db());

  private static final AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 7896, datastoreRule.getConnectionURL());
  private final ServiceProviderClient client = ServiceProviderClientFactory.create("http://localhost:7896");

  @BeforeClass
  public static void setUp() throws Exception {
    bootstrap.start();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    bootstrap.stop();
  }

  @Test
  public void create() throws Exception {
    List<Network3GPP> newtworks = Lists.newArrayList(new Network3GPP("name", "333", "222"));
    List<String> domNames = Lists.newArrayList("dNames");
    List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "222222"));
    List<Auth> auths = Lists.newArrayList(new Auth("CREDENTIAL_TYPE", "SIM_1"));
    List<EAP> eaps = Lists.newArrayList(new EAP("CRYPTO_CARD", auths));
    NAI nai = new NAI("name", "UTF_8", eaps);
    List<NAI> naiRealms = Lists.newArrayList(nai);

    NewServiceProvider newProvider = new NewServiceProvider("name", "description", newtworks, domNames, consortiums, naiRealms);

    ID id = client.create(newProvider);

    ServiceProvider provider = new ServiceProvider(id.value, "name", "description", newtworks, domNames, consortiums, naiRealms);
    List<ServiceProvider> want = Lists.newArrayList(provider);

    List<ServiceProvider> got = client.findAll();

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    List<Network3GPP> newtworks = Lists.newArrayList(new Network3GPP("name", "333", "222"));
    List<String> domNames = Lists.newArrayList("dNames");
    List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "222222"));
    List<Auth> auths = Lists.newArrayList(new Auth("CREDENTIAL_TYPE", "SIM_1"));
    List<EAP> eaps = Lists.newArrayList(new EAP("CRYPTO_CARD", auths));
    NAI nai = new NAI("name", "UTF_8", eaps);
    List<NAI> naiRealms = Lists.newArrayList(nai);

    NewServiceProvider newProvider = new NewServiceProvider("name", "description", newtworks, domNames, consortiums, naiRealms);

    ID id = client.create(newProvider);

    List<Network3GPP> newNewtworks = Lists.newArrayList(new Network3GPP("name", "333", "222"));
    List<String> newDomNames = Lists.newArrayList("dNames");
    List<RoamingConsortium> newConsortiums = Lists.newArrayList(new RoamingConsortium("name", "222222"));
    List<Auth> newAuths = Lists.newArrayList(new Auth("CREDENTIAL_TYPE", "SIM_1"));
    List<EAP> newEaps = Lists.newArrayList(new EAP("CRYPTO_CARD", newAuths));
    NAI newNai = new NAI("name", "UTF_8", newEaps);
    List<NAI> newNaiRealms = Lists.newArrayList(newNai);

    ServiceProvider provider = new ServiceProvider(id.value, "Stamat", "description 12", newNewtworks, newDomNames, newConsortiums, newNaiRealms);

    client.update(id.value, provider);

    ServiceProvider got = client.findByID(id.value);

    assertThat(got, deepEquals(provider));
  }

  @Test
  public void delete() throws Exception {
    List<Network3GPP> newtworks = Lists.newArrayList();
    List<String> domNames = Lists.newArrayList();
    List<RoamingConsortium> consortiums = Lists.newArrayList();
    List<NAI> naiRealms = Lists.newArrayList();

    NewServiceProvider newProvider = new NewServiceProvider("name", "description", newtworks, domNames, consortiums, naiRealms);

    ID id = client.create(newProvider);

    client.delete(id.value);

    List<ServiceProvider> got = client.findAll();

    assertTrue(got.isEmpty());
  }
}
