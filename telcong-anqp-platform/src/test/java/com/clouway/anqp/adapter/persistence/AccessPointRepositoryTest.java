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

import static com.clouway.anqp.IPv4.Availability.PUBLIC;
import static com.clouway.anqp.NewAccessPointBuilder.newAP;
import static com.clouway.anqp.NewOperatorBuilder.newOperator;
import static com.clouway.anqp.OperatorState.INACTIVE;
import static com.clouway.anqp.VenueBuilder.newVenueBuilder;
import static com.clouway.anqp.VenueName.defaultName;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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
  private PersistentOperatorRepository operatorRepository = new PersistentOperatorRepository(datastore);

  private IPv4AvailabilityCatalog v4Catalog = context.mock(IPv4AvailabilityCatalog.class);

  private PersistentAccessPointRepository repository = new PersistentAccessPointRepository(datastore, v4Catalog);

  @Test
  public void createApWithoutVenueNames() throws Exception {
    Venue venue = newVenueBuilder().group("group").type("type").build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));
    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();

    Venue wantedVenue = newVenueBuilder().group("group").type("type").names(defaultName()).build();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", wantedVenue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    assertThat(got, deepEquals(want));
  }

  @Test(expected = OperatorException.class)
  public void createApForInactiveOperator() throws Exception {
    NewOperator operator = newOperator().state(INACTIVE).build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = newAP().operatorId(new ID(operID)).build();
    repository.create(ap);
  }

  @Test
  public void findById() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();

    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

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
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    Object id = repository.create(ap);

    AccessPoint got = repository.findByIp("ip").get();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownIp() throws Exception {
    Optional<AccessPoint> found = repository.findByIp("ip");

    assertFalse(found.isPresent());
  }

  @Test
  public void findQueryList() throws Exception {
    NewOperator operator = newOperator().iPv4(new IPv4(PUBLIC)).build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = NewAccessPointBuilder.newAP().operatorId(new ID(operID)).build();
    Object apID = repository.create(ap);

    context.checking(new Expectations() {{
      oneOf(v4Catalog).findAvailability("PUBLIC");
      will(returnValue(Optional.of(PUBLIC)));
    }});

    QueryList actual = repository.findQueryList(apID);
    QueryList expected = new QueryList(1);

    assertThat(actual, deepEquals(expected));
  }

  @Test(expected = NotFoundException.class)
  public void findQueryListForMissingAp() throws Exception {
    repository.findQueryList("id");
  }

  @Test(expected = NotFoundException.class)
  public void findQueryListForMissingOperator() throws Exception {
    NewAccessPoint ap = NewAccessPointBuilder.newAP().operatorId(new ID("someId")).build();

    Object apID = repository.create(ap);

    repository.findQueryList(apID);
  }

  @Test
  public void findAll() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap1 = new NewAccessPoint(new ID(operID), "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));
    NewAccessPoint ap2 = new NewAccessPoint(new ID(operID), "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    Object apID1 = repository.create(ap1);
    Object apID2 = repository.create(ap2);

    List<AccessPoint> got = repository.findAll();

    List<AccessPoint> want = Lists.newArrayList(
            new AccessPoint(new ID(apID1), "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List")))),
            new AccessPoint(new ID(apID2), "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))))
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findCapabilities() throws Exception {
    // Capabilities are ordered in non-decreasing id value e.g. 256, 257, 258 ...
    CapabilityList capabilities = new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"), new Capability(257, "ANQP Capability list")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();

    NewOperator operator = newOperator().build();

    Object operatorId = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operatorId), "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue, geo, civic, capabilities);

    Object id = repository.create(ap);

    CapabilityList actual = repository.findCapabilityList(new ID(id));

    assertThat(actual, deepEquals(capabilities));
  }

  @Test(expected = NotFoundException.class)
  public void findCapabilitiesForMissingAP() throws Exception {
    repository.findCapabilityList(new ID("id1"));
  }

  @Test
  public void update() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    Venue newVenue = newVenueBuilder().names(new VenueName("Info2", new Language("en2"))).build();

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocation newCivic = new CivicLocation("country2", "city2", "street2", "number2", "postCode2");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);
    GeoLocation newGeo = new GeoLocation(55.55555, 66.666666);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint newAP = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));
    Object apID = repository.create(newAP);

    AccessPoint ap = new AccessPoint(new ID(apID), "ip2", new MacAddress("bb:aa"), "sn2", "model2", newVenue, newGeo, newCivic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    AccessPointRequest request = new AccessPointRequest(new ID(apID), "ip2", new MacAddress("bb:aa"), "sn2", "model2", newVenue, newGeo, newCivic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    repository.update(request);

    AccessPoint got = repository.findById(apID).get();

    assertThat(got, deepEquals(ap));
  }

  @Test
  public void deleteById() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = newAP().operatorId(new ID(operID)).build();
    Object id = repository.create(ap);

    repository.delete(id);

    List<AccessPoint> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = newAP().operatorId(new ID(operID)).build();
    repository.create(ap);

    repository.delete("id");

    List<AccessPoint> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}