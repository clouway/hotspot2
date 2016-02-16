package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.IpType.PUBLIC;
import static com.clouway.anqp.OperatorState.ACTIVE;
import static com.clouway.anqp.VenueBuilder.newVenueBuilder;
import static com.clouway.anqp.VenueName.defaultName;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 */
public class AccessPointRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private IpTypeCatalog catalog = context.mock(IpTypeCatalog.class);
  private PersistentAccessPointRepository repository = new PersistentAccessPointRepository(datastore, catalog);
  private PersistentOperatorRepository operatorRepository = new PersistentOperatorRepository(datastore);

  @Test
  public void createApWithoutVenueNames() throws Exception {
    Venue venue = newVenueBuilder().group("group").type("type").build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    NewAccessPoint ap = new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();

    Venue wantedVenue = newVenueBuilder().group("group").type("type").names(defaultName()).build();
    AccessPoint want = new AccessPoint(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", wantedVenue);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findById() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    NewAccessPoint ap = new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();
    AccessPoint want = new AccessPoint(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<AccessPoint> found = repository.findById("id");

    assertFalse(found.isPresent());
  }

  @Test
  public void findByIp() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    NewAccessPoint ap = new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    Object id = repository.create(ap);

    AccessPoint got = repository.findByIp("ip").get();
    AccessPoint want = new AccessPoint(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownIp() throws Exception {
    Optional<AccessPoint> found = repository.findByIp("ip");

    assertFalse(found.isPresent());
  }

  @Test
  public void findQueryList() throws Exception {
    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    NewAccessPoint ap = new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    context.checking(new Expectations() {{
      oneOf(catalog).findId("PUBLIC");
      will(returnValue(Optional.of(1)));
    }});

    Object apId = repository.create(ap);

    QueryList actual = repository.findQueryList(apId);
    QueryList expected = new QueryList(1);

    assertThat(actual, deepEquals(expected));
  }

  @Test(expected = NotFoundException.class)
  public void findQueryListForMissingAp() throws Exception {
    repository.findQueryList("apId");
  }

  @Test(expected = NotFoundException.class)
  public void findQueryListForMissingOperator() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    NewAccessPoint ap = new NewAccessPoint("operatorId", "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue);

    Object apId = repository.create(ap);

    repository.findQueryList(apId);
  }

  @Test
  public void findAll() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    Object operatorId1 = operatorRepository.create(new NewOperator("name1", ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1", PUBLIC));
    Object operatorId2 = operatorRepository.create(new NewOperator("name2", ACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber2", PUBLIC));

    NewAccessPoint ap1 = new NewAccessPoint(operatorId1, "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue);
    NewAccessPoint ap2 = new NewAccessPoint(operatorId2, "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue);

    Object id1 = repository.create(ap1);
    Object id2 = repository.create(ap2);

    List<AccessPoint> got = repository.findAll();

    List<AccessPoint> want = Lists.newArrayList(
            new AccessPoint(id1, "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue),
            new AccessPoint(id2, "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue)
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Venue venue = newVenueBuilder().group("group").type("type").names(new VenueName("Info", new Language("en"))).build();
    Venue newVenue = newVenueBuilder().group("group").type("type").names(
            new VenueName("Info", new Language("en")),
            new VenueName("Info2", new Language("Bg"))).build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    Object id = repository.create(new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb"), "sn", "model", venue));

    AccessPoint ap = new AccessPoint(id, "ip2", new MacAddress("bb:aa"), "sn2", "model2", newVenue);

    repository.update(ap);

    AccessPoint found = repository.findById(id).get();

    assertThat(found, deepEquals(ap));
  }

  @Test
  public void deleteById() throws Exception {
    Venue venue = newVenueBuilder().build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    Object id = repository.create(new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb"), "sn", "model", venue));

    repository.delete(id);

    List<AccessPoint> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    Venue venue = newVenueBuilder().build();

    Object operatorId = operatorRepository.create(new NewOperator("name", ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC));

    repository.create(new NewAccessPoint(operatorId, "ip", new MacAddress("aa:bb"), "sn", "model", venue));

    repository.delete("id");

    List<AccessPoint> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}