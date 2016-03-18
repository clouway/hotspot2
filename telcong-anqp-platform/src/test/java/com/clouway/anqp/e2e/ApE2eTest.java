package com.clouway.anqp.e2e;


import com.clouway.anqp.AnqpPlatformBootstrap;
import com.clouway.anqp.adapter.persistence.PersistentDatastoreRule;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.ap.*;
import com.clouway.telcong.anqp.client.capability.Capability;
import com.clouway.telcong.anqp.client.operator.NewOperator;
import com.clouway.telcong.anqp.client.operator.OperatorClient;
import com.clouway.telcong.anqp.client.operator.OperatorClientFactory;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApE2eTest {

  @ClassRule
  public static PersistentDatastoreRule datastoreRule = new PersistentDatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private static final AnqpPlatformBootstrap bootstrap = new AnqpPlatformBootstrap(1620, 7896, datastoreRule.getConnectionURL());

  private final AccessPointClient apClient = AccessPointClientFactory.create("http://localhost:7896");
  private final OperatorClient opClient = OperatorClientFactory.create("http://localhost:7896");

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
    NewOperator operator = new NewOperator("name1", "ACTIVE", "description", "domainName", "friendlyName", "emergencyName", "PUBLIC", "NOT_AVAILABLE");

    // 1. create operator
    ID opId = opClient.create(operator);

    NewAccessPoint ap = new NewAccessPoint(
            opId.value,
            "ip",
            "mac",
            "sn",
            "model",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(256, 257)
    );

    // 2. create access point for operator
    ID apId = apClient.create(ap);

    // 3. find access point
    AccessPoint actual = apClient.findByID(apId.value);

    AccessPoint expected = new AccessPoint(
            apId.value,
            "ip",
            "ma:c",
            "sn",
            "model",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(new Capability(256, "ANQP Query List"), new Capability(257, "ANQP Capability list"))
    );

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void update() throws Exception {
    NewOperator operator = new NewOperator("name1", "ACTIVE", "description", "domainName", "friendlyName", "emergencyName", "PUBLIC", "NOT_AVAILABLE");

    // 1. create operator
    ID opId = opClient.create(operator);

    NewAccessPoint ap = new NewAccessPoint(
            opId.value,
            "ip1",
            "mac1",
            "sn1",
            "model1",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(256, 257)
    );

    // 2. create access point for operator
    ID apId = apClient.create(ap);

    AccessPointRequest request = new AccessPointRequest(
            apId.value,
            "ip2",
            "mac2",
            "sn2",
            "model2",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(256, 257)
    );

    // 3. update access point
    apClient.update(apId.value, request);

    AccessPoint actual = apClient.findByID(apId.value);

    AccessPoint expected = new AccessPoint(
            apId.value,
            "ip2",
            "ma:c2",
            "sn2",
            "model2",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(new Capability(256, "ANQP Query List"), new Capability(257, "ANQP Capability list"))
    );

    assertThat(actual, deepEquals(expected));
  }

  @Test
  public void delete() throws Exception {
    NewOperator operator = new NewOperator("name1", "ACTIVE", "description", "domainName", "friendlyName", "emergencyName", "PUBLIC", "NOT_AVAILABLE");

    // 1. create operator
    ID opId = opClient.create(operator);

    NewAccessPoint ap = new NewAccessPoint(
            opId.value,
            "ip1",
            "mac1",
            "sn1",
            "model1",
            new Venue("business", "research-and-dev-facility", Lists.newArrayList(new VenueName("name1", "en"))),
            new GeoLocation(10.5, 20.5),
            new CivicLocation("country", "city", "street", "streetNumber", "postCode"),
            Lists.newArrayList(256, 257)
    );

    // 2. create access point for operator
    ID apId = apClient.create(ap);

    // 3. delete access point
    apClient.delete(apId.value);

    List<AccessPoint> accessPoints = apClient.findAll();

    assertTrue(accessPoints.isEmpty());
  }
}
