package com.clouway.anqp.e2e;

import com.clouway.anqp.AnqpPlatformBootstrap;
import com.clouway.anqp.adapter.persistence.PersistentDatastoreRule;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.operator.NewOperator;
import com.clouway.telcong.anqp.client.operator.Operator;
import com.clouway.telcong.anqp.client.operator.OperatorClient;
import com.clouway.telcong.anqp.client.operator.OperatorClientFactory;
import com.clouway.telcong.anqp.client.rg.NewRoamingGroup;
import com.clouway.telcong.anqp.client.rg.RoamingGroup;
import com.clouway.telcong.anqp.client.rg.RoamingGroupClient;
import com.clouway.telcong.anqp.client.rg.RoamingGroupClientFactory;
import com.clouway.telcong.anqp.client.rg.RoamingGroupRequest;
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
public class RoamingGroupEndToEndTest {
  @ClassRule
  public static PersistentDatastoreRule datastoreRule = new PersistentDatastoreRule();

  @Rule
  public DatastoreCleaner cleaner = new DatastoreCleaner(datastoreRule.db());

  private static AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 1212, datastoreRule.getConnectionURL());

  private RoamingGroupClient groupClient = RoamingGroupClientFactory.create("http://localhost:1212");
  private OperatorClient operClient = OperatorClientFactory.create("http://localhost:1212");

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
    NewRoamingGroup newGroup = new NewRoamingGroup("name", "descr", "INTERNATIONAL");
    ID id = groupClient.create(newGroup);

    RoamingGroupRequest group = new RoamingGroupRequest(id.value, "newName", "newDescr", "NATIONAL");
    groupClient.update(id.value, group);

    RoamingGroup got = groupClient.findByID(id.value);

    List<Operator> operators = Lists.newArrayList();
    RoamingGroup want = new RoamingGroup(id.value, "newName", "newDescr", "NATIONAL", operators);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void assignOperator() throws Exception {
    NewOperator newOper = new NewOperator("name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID operID = operClient.create(newOper);

    NewRoamingGroup newGroup = new NewRoamingGroup("name", "descr", "INTERNATIONAL");
    ID rgID = groupClient.create(newGroup);

    List<Object> operIDs = Lists.newArrayList(operID.value);
    groupClient.assignOperators(rgID.value, operIDs);

    RoamingGroup got = groupClient.findByID(rgID.value);

    Operator oper = new Operator(operID.value, "name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    List<Operator> operators = Lists.newArrayList(oper);
    RoamingGroup want = new RoamingGroup(rgID.value, "name", "descr", "INTERNATIONAL", operators);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void removeAssignedOperators() throws Exception {
    NewOperator newOper = new NewOperator("name", "ACTIVE", "descr", "dName", "fName", "112", "PUBLIC", "AVAILABLE");
    ID operID = operClient.create(newOper);

    NewRoamingGroup newGroup = new NewRoamingGroup("name", "descr", "INTERNATIONAL");
    ID rgID = groupClient.create(newGroup);

    List<Object> operIDs = Lists.newArrayList(operID.value);
    groupClient.assignOperators(rgID.value, operIDs);
    groupClient.removeOperators(rgID.value, operIDs);

    List<RoamingGroup> got = groupClient.findAll();

    List<Operator> operators = Lists.newArrayList();
    List<RoamingGroup> want = Lists.newArrayList(new RoamingGroup(rgID.value, "name", "descr", "INTERNATIONAL", operators));

    assertThat(got, deepEquals(want));
  }

  @Test
  public void delete() throws Exception {
    NewRoamingGroup newGroup = new NewRoamingGroup("name", "descr", "INTERNATIONAL");
    ID rgID = groupClient.create(newGroup);

    groupClient.delete(rgID.value);
    List<RoamingGroup> got = groupClient.findAll();

    assertTrue(got.isEmpty());
  }
}
