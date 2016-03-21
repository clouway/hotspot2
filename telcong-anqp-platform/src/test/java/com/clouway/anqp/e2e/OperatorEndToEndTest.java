package com.clouway.anqp.e2e;

import com.clouway.anqp.*;
import com.clouway.anqp.adapter.persistence.PersistentDatastoreRule;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.ap.*;
import com.clouway.telcong.anqp.client.ap.AccessPoint;
import com.clouway.telcong.anqp.client.ap.CivicLocation;
import com.clouway.telcong.anqp.client.ap.GeoLocation;
import com.clouway.telcong.anqp.client.ap.NewAccessPoint;
import com.clouway.telcong.anqp.client.ap.Venue;
import com.clouway.telcong.anqp.client.ap.VenueName;
import com.clouway.telcong.anqp.client.capability.Capability;
import com.clouway.telcong.anqp.client.operator.NewOperator;
import com.clouway.telcong.anqp.client.operator.Operator;
import com.clouway.telcong.anqp.client.operator.OperatorClient;
import com.clouway.telcong.anqp.client.operator.OperatorClientFactory;
import com.clouway.telcong.anqp.client.sp.*;
import com.clouway.telcong.anqp.client.sp.Auth;
import com.clouway.telcong.anqp.client.sp.EAP;
import com.clouway.telcong.anqp.client.sp.NAI;
import com.clouway.telcong.anqp.client.sp.Network3GPP;
import com.clouway.telcong.anqp.client.sp.NewServiceProvider;
import com.clouway.telcong.anqp.client.sp.RoamingConsortium;
import com.clouway.telcong.anqp.client.sp.ServiceProvider;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 */
public class OperatorEndToEndTest {
  @ClassRule
  public static PersistentDatastoreRule datastoreRule = new PersistentDatastoreRule();

  @Rule
  public DatastoreCleaner cleaner = new DatastoreCleaner(datastoreRule.db());

  private static AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 1212, datastoreRule.getConnectionURL());
  private OperatorClient operClient = OperatorClientFactory.create("http://localhost:1212");
  private AccessPointClient apClient = AccessPointClientFactory.create("http://localhost:1212");
  private ServiceProviderClient spClient = ServiceProviderClientFactory.create("http://localhost:1212");

  @BeforeClass
  public static void setUp() {
    bootstrap.start();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    bootstrap.stop();
  }

  @Test
  public void update() throws Exception {
    NewOperator newOper = new NewOperator("name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID id = operClient.create(newOper);

    Operator oper = new Operator(id.value, "name", "INACTIVE", "newDescr", "newDName", "newFName", "112", "SINGLE_NAT_PRIVATE", "UNKNOWN");

    operClient.update(id.value, oper);

    Operator got = operClient.findByID(id.value);

    assertThat(got, deepEquals(oper));
  }

  @Test
  public void assignAccessPoints() throws Exception {
    NewOperator newOper1 = new NewOperator("name1", "ACTIVE", "descr1", "dName1", "fName1", "112", "PUBLIC", "AVAILABLE");
    ID operID1 = operClient.create(newOper1);

    NewOperator newOper2 = new NewOperator("name2", "ACTIVE", "descr2", "dName2", "fName2", "911", "PUBLIC", "AVAILABLE");
    ID operID2 = operClient.create(newOper2);

    List<VenueName> names = Lists.newArrayList(new VenueName("name", "en"));
    Venue venue = new Venue("business", "research-and-dev-facility", Lists.newArrayList(names));
    GeoLocation geoLocation = new GeoLocation(22.2222, 33.3333);
    CivicLocation civicLocation = new CivicLocation("country", "city", "street", "sn", "postCode");
    List<Integer> capabilityIds = Lists.newArrayList(256);

    NewAccessPoint newAP = new NewAccessPoint(operID1.value, "ip", "aa:bb", "sn", "model", venue, geoLocation, civicLocation, capabilityIds);
    ID apID = apClient.create(newAP);
    List<Object> apIDs = Lists.newArrayList(apID.value);

    operClient.assignAccessPoints(operID2.value, apIDs);

    List<AccessPoint> got = operClient.findAccessPoints(operID2.value);

    List<Capability> capabilities = Lists.newArrayList(new Capability(256, "ANQP Query List"));
    List<AccessPoint> want = Lists.newArrayList(new AccessPoint(apID.value,  "ip", "aa:bb", "sn", "model", venue, geoLocation, civicLocation, capabilities));

    assertThat(got, deepEquals(want));
  }

  @Test
  public void assignServiceProviders() throws Exception {
    NewOperator oper = new NewOperator("name", "INACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID operID = operClient.create(oper);

    List<Network3GPP> newtworks = Lists.newArrayList(new Network3GPP("name", "333", "222"));
    List<String> domNames = Lists.newArrayList("dNames");
    List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "222222"));
    List<Auth> auths = Lists.newArrayList(new Auth("CREDENTIAL_TYPE", "SIM_1"));
    List<EAP> eaps = Lists.newArrayList(new EAP("CRYPTO_CARD", auths));
    NAI nai = new NAI("name", "UTF_8", eaps);
    List<NAI> naiRealms = Lists.newArrayList(nai);

    NewServiceProvider newProvider = new NewServiceProvider("name", "description", newtworks, domNames, consortiums, naiRealms);
    ID spID = spClient.create(newProvider);

    List<Object> spIDs = Lists.newArrayList(spID.value);
    operClient.assignServiceProviders(operID.value, spIDs);

    List<ServiceProvider> got = operClient.findServiceProviders(operID.value);
    List<ServiceProvider> want = Lists.newArrayList(new ServiceProvider(spID.value, "name", "description", newtworks, domNames, consortiums, naiRealms));

    assertThat(got, deepEquals(want));
  }

  @Test
  public void removeServiceProviders() throws Exception {
    NewOperator oper = new NewOperator("name", "INACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID operID = operClient.create(oper);

    List<Network3GPP> newtworks = Lists.newArrayList(new Network3GPP("name", "333", "222"));
    List<String> domNames = Lists.newArrayList("dNames");
    List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "222222"));
    List<Auth> auths = Lists.newArrayList(new Auth("CREDENTIAL_TYPE", "SIM_1"));
    List<EAP> eaps = Lists.newArrayList(new EAP("CRYPTO_CARD", auths));
    NAI nai = new NAI("name", "UTF_8", eaps);
    List<NAI> naiRealms = Lists.newArrayList(nai);

    NewServiceProvider newProvider = new NewServiceProvider("name", "description", newtworks, domNames, consortiums, naiRealms);
    ID spID = spClient.create(newProvider);

    List<Object> spIDs = Lists.newArrayList(spID.value);
    operClient.assignServiceProviders(operID.value, spIDs);
    operClient.removeServiceProviders(operID.value, spIDs);

    List<ServiceProvider> got = operClient.findServiceProviders(operID.value);

    assertTrue(got.isEmpty());
  }

  @Test
  public void activate() throws Exception {
    NewOperator oper = new NewOperator("name", "INACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID id = operClient.create(oper);

    operClient.activate(id.value);

    Operator got = operClient.findByID(id.value);
    Operator want = new Operator(id.value, "name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void deactivate() throws Exception {
    NewOperator oper = new NewOperator("name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID id = operClient.create(oper);

    operClient.deactivate(id.value);

    Operator got = operClient.findByID(id.value);
    Operator want = new Operator(id.value, "name", "INACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void delete() throws Exception {
    NewOperator oper = new NewOperator("name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID id = operClient.create(oper);

    operClient.delete(id.value);

    List<Operator> got = operClient.findAll();

    assertTrue(got.isEmpty());
  }
}
