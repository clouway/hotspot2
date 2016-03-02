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

import static com.clouway.anqp.NewAccessPointBuilder.newAP;
import static com.clouway.anqp.NewOperatorBuilder.newOperator;
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
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);
    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();

    Venue wantedVenue = newVenueBuilder().group("group").type("type").names(defaultName()).build();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", wantedVenue, geo, civic);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findById() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();

    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);

    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);

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

    NewAccessPoint ap = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);

    Object id = repository.create(ap);

    AccessPoint got = repository.findByIp("ip").get();
    AccessPoint want = new AccessPoint(new ID(id), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownIp() throws Exception {
    Optional<AccessPoint> found = repository.findByIp("ip");

    assertFalse(found.isPresent());
  }

  @Test
  public void findQueryList() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap = NewAccessPointBuilder.newAP().operatorId(new ID(operID)).build();
    Object apID = repository.create(ap);

    context.checking(new Expectations() {{
      oneOf(catalog).findId("PUBLIC");
      will(returnValue(Optional.of(1)));
    }});

    QueryList actual = repository.findQueryList(apID);
    QueryList expected = new QueryList(1);

    assertThat(actual, deepEquals(expected));
  }

  @Test(expected = NotFoundException.class)
  public void findQueryListForMissingAp() throws Exception {
    repository.findQueryList("id");
  }

  @Test
  public void findAll() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.22222, 33.333333);

    NewOperator operator = newOperator().build();
    Object operID = operatorRepository.create(operator);

    NewAccessPoint ap1 = new NewAccessPoint(new ID(operID), "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue, geo, civic);
    NewAccessPoint ap2 = new NewAccessPoint(new ID(operID), "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue, geo, civic);

    Object apID1 = repository.create(ap1);
    Object apID2 = repository.create(ap2);

    List<AccessPoint> got = repository.findAll();

    List<AccessPoint> want = Lists.newArrayList(
            new AccessPoint(new ID(apID1), "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1", venue, geo, civic),
            new AccessPoint(new ID(apID2), "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2", venue, geo, civic)
    );

    assertThat(got, deepEquals(want));
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

    NewAccessPoint newAP = new NewAccessPoint(new ID(operID), "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model", venue, geo, civic);
    Object apID = repository.create(newAP);

    AccessPoint ap = new AccessPoint(new ID(apID), "ip2", new MacAddress("bb:aa"), "sn2", "model2", newVenue, newGeo, newCivic);

    repository.update(ap);

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