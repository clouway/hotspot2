package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.NewAccessPointBuilder.newAP;
import static com.clouway.anqp.NewOperatorBuilder.newOperator;
import static com.clouway.anqp.NewServiceProviderBuilder.newServiceProvider;
import static com.clouway.anqp.VenueBuilder.newVenueBuilder;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 */
public class OperatorRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private IpTypeCatalog catalog = context.mock(IpTypeCatalog.class);

  private AccessPointRepository accessPointRepository = new PersistentAccessPointRepository(datastore, catalog);
  private RoamingGroupRepository groupRepository = new PersistentRoamingGroupRepository(datastore);
  private ServiceProviderRepository providerRepository = new PersistentServiceProviderRepository(datastore);
  private OperatorRepository operRepository = new PersistentOperatorRepository(datastore);


  @Test(expected = OperatorException.class)
  public void createOperatorWithExistingName() throws Exception {
    NewOperator someOperator = new NewOperator("sameName", OperatorState.ACTIVE, "descr", "dName", "fName", "emergency", IpType.PUBLIC);
    operRepository.create(someOperator);

    NewOperator anotherOperator = new NewOperator("sameName", OperatorState.ACTIVE, "anotherDescr", "anotherDName", "anotherFName", "anotherEmergencyNumber", IpType.PUBLIC);
    operRepository.create(anotherOperator);
  }

  @Test
  public void findById() throws Exception {
    Object id = operRepository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", IpType.PUBLIC));

    Operator got = operRepository.findById(new ID(id)).get();
    Operator want = new Operator(new ID(id), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", IpType.PUBLIC);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<Operator> got = operRepository.findById(new ID("id"));

    assertFalse(got.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    Object id1 = operRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1", IpType.PUBLIC));
    Object id2 = operRepository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber2", IpType.PUBLIC));

    List<Operator> got = operRepository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(id1), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1", IpType.PUBLIC),
            new Operator(new ID(id2), "name2", OperatorState.INACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber2", IpType.PUBLIC)
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = operRepository.create(new NewOperator("name", OperatorState.ACTIVE, "descr", "dName", "fName", "123", IpType.PUBLIC));

    Operator operator = new Operator(new ID(id), "name", OperatorState.INACTIVE, "newDescr", "newDomainName", "newFriendlyName", "*88", IpType.PUBLIC);

    operRepository.update(operator);

    Operator found = operRepository.findById(new ID(id)).get();

    assertThat(found, deepEquals(operator));
  }

  @Test
  public void updateOperatorName() throws Exception {
    Object id = operRepository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "dName", "fName", "911", IpType.PUBLIC));

    Operator operator = new Operator(new ID(id), "newName", OperatorState.ACTIVE, "description", "dName", "fName", "911", IpType.PUBLIC);

    operRepository.update(operator);

    Operator got = operRepository.findById(new ID(id)).get();
    Operator want = new Operator(new ID(id), "newName", OperatorState.ACTIVE, "description", "dName", "fName", "911", IpType.PUBLIC);

    assertThat(got, deepEquals(want));
  }

  @Test(expected = OperatorException.class)
  public void updateOperatorWithReservedName() throws Exception {
    Object someID = operRepository.create(new NewOperator("someName", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "123", IpType.PUBLIC));
    operRepository.create(new NewOperator("existName", OperatorState.ACTIVE, "anotherDescription", "anotherDomainName", "anotherFriendlyName", "1234", IpType.PUBLIC));

    Operator operator = new Operator(new ID(someID), "existName", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "123", IpType.PUBLIC);

    operRepository.update(operator);
  }

  @Test
  public void activate() throws Exception {
    Object someID = operRepository.create(new NewOperator("name1", OperatorState.INACTIVE, "description1", "dName1", "fName1", "112", IpType.PUBLIC));
    Object anotherID = operRepository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "911", IpType.PUBLIC));

    operRepository.activate(new ID(someID));

    List<Operator> got = operRepository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(anotherID), "name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "911", IpType.PUBLIC),
            new Operator(new ID(someID), "name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "112", IpType.PUBLIC)
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = NotFoundException.class)
  public void activateUnknownOperator() throws Exception {
    operRepository.activate(new ID("id"));
  }

  @Test
  public void deactivate() throws Exception {
    Object someID = operRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "112", IpType.PUBLIC));
    Object anotherID = operRepository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "911", IpType.PUBLIC));

    operRepository.deactivate(new ID(someID));

    List<Operator> got = operRepository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(anotherID), "name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "911", IpType.PUBLIC),
            new Operator(new ID(someID), "name1", OperatorState.INACTIVE, "description1", "dName1", "fName1", "112", IpType.PUBLIC)
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = OperatorException.class)
  public void deactivateOperatorAssignedToRoamingGroup() throws Exception {
    Object operID = operRepository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "dName", "fName", "112", IpType.PUBLIC));
    Object groupID = groupRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.NATIONAL));

    groupRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID)));

    operRepository.deactivate(new ID(operID));
  }

  @Test(expected = OperatorException.class)
  public void deactivateOperatorWithAssignedAccessPoints() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operRepository.create(operator);
    ID operatorID = new ID(operID);

    NewAccessPoint ap = newAP().operatorId(operatorID).build();

    Object apID = accessPointRepository.create(ap);

    List<ID> apIDs = Lists.newArrayList(new ID(apID));

    operRepository.assignAccessPoints(operatorID, apIDs);

    operRepository.deactivate(operatorID);
  }

  @Test(expected = NotFoundException.class)
  public void deactivateUnknownOperator() throws Exception {
    operRepository.deactivate(new ID("operID"));
  }

  @Test
  public void assignAccessPoints() throws Exception {
    Venue venue = newVenueBuilder().names(new VenueName("Info", new Language("en"))).build();
    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geo = new GeoLocation(22.2222222, 33.3333333);

    NewOperator operator = newOperator().build();
    Object operID = operRepository.create(operator);

    NewAccessPoint ap1 = new NewAccessPoint(new ID(operID), "ip1", new MacAddress("aa:bb:cc"), "sn1", "model1", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));
    NewAccessPoint ap2 = new NewAccessPoint(new ID(operID), "ip2", new MacAddress("cc:bb:aa"), "sn2", "model2", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    Object apID1 = accessPointRepository.create(ap1);
    Object apID2 = accessPointRepository.create(ap2);
    List<ID> apIDs = Lists.newArrayList(new ID(apID1), new ID(apID2));

    operRepository.assignAccessPoints(new ID(operID), apIDs);

    List<AccessPoint> got = operRepository.findAccessPoints(new ID(operID));

    List<AccessPoint> want = Lists.newArrayList(
            new AccessPoint(new ID(apID1), "ip1", new MacAddress("aa:bb:cc"), "sn1", "model1", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List")))),
            new AccessPoint(new ID(apID2), "ip2", new MacAddress("cc:bb:aa"), "sn2", "model2", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))))
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = NotFoundException.class)
  public void findAPsOfUnknownOperator() throws Exception {
    ID id = new ID("id");

    operRepository.findAccessPoints(id);
  }

  @Test(expected = NotFoundException.class)
  public void assignAPsToUnknownOperator() throws Exception {
    List<ID> apIDs = Lists.newArrayList(new ID("id1"), new ID("id2"));

    operRepository.assignAccessPoints(new ID("operID3"), apIDs);
  }

  @Test(expected = NotFoundException.class)
  public void assignAccessPointsToInactiveOperator() throws Exception {
    Object operID = operRepository.create(new NewOperator("name", OperatorState.INACTIVE, "description", "dName", "fName", "911", IpType.PUBLIC));

    List<ID> apIDs = Lists.newArrayList(new ID("id1"), new ID("id2"));
    operRepository.assignAccessPoints(new ID(operID), apIDs);
  }

  @Test
  public void assignServiceProviders() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operRepository.create(operator);

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "123", "12"));
    DomainNameList list = new DomainNameList(Lists.newArrayList("dName"));
    List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "0xAABBCC"));
    List<NAI> nais = Lists.newArrayList(new NAI("name", Encoding.UTF_8));

    NewServiceProvider newSP = new NewServiceProvider("name1", "descr1", networks, list, consortiums, nais);
    Object spID = providerRepository.create(newSP);

    List<ID> spIDs = Lists.newArrayList(new ID(spID));
    operRepository.assignServiceProviders(new ID(operID), spIDs);

    List<ServiceProvider> got = operRepository.findServiceProviders(new ID(operID));

    List<ServiceProvider> want = Lists.newArrayList(new ServiceProvider(new ID(spID), "name1", "descr1", networks, list, consortiums, nais));

    assertThat(got, deepEquals(want));
  }

  @Test(expected = NotFoundException.class)
  public void assignServiceProvidersToUnknownOperator() throws Exception {
    List<ID> spIDs = Lists.newArrayList(new ID("spID"));
    operRepository.assignServiceProviders(new ID("operID"), spIDs);
  }

  @Test(expected = NotFoundException.class)
  public void findServiceProvidersForUnknownOperator() throws Exception {
    operRepository.findServiceProviders(new ID("id"));
  }

  @Test
  public void removeServiceProviders() throws Exception {
    NewOperator operator = newOperator().build();
    Object operID = operRepository.create(operator);

    NewServiceProvider newSP = newServiceProvider().build();
    Object spID = providerRepository.create(newSP);

    List<ID> spIDs = Lists.newArrayList(new ID(spID));
    operRepository.assignServiceProviders(new ID(operID), spIDs);

    operRepository.removeServiceProviders(new ID(operID), spIDs);

    List<ServiceProvider> got = operRepository.findServiceProviders(new ID(operID));

    assertTrue(got.isEmpty());
  }

  @Test(expected = NotFoundException.class)
  public void removeServiceProvidersFromUnknownOperator() throws Exception {
    ID operID = new ID("operID");
    List<ID> spIDs = Lists.newArrayList(new ID("spID"));
    operRepository.removeServiceProviders(operID, spIDs);
  }

  @Test
  public void setEmergencyNumber() throws Exception {
    Object id1 = operRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "911", IpType.PUBLIC));
    Object id2 = operRepository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "1234", IpType.PUBLIC));

    NewEmergencyNumber number = new NewEmergencyNumber(new ID(id1), "112");

    operRepository.updateEmergencyNumber(number);

    List<Operator> got = operRepository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(id2), "name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "1234", IpType.PUBLIC),
            new Operator(new ID(id1), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "112", IpType.PUBLIC)
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = EmergencyNumberException.class)
  public void setAlreadyExistingEmergencyNumber() throws Exception {
    Object id1 = operRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "911", IpType.PUBLIC));
    operRepository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "112", IpType.PUBLIC));

    NewEmergencyNumber number = new NewEmergencyNumber(new ID(id1), "112");
    operRepository.updateEmergencyNumber(number);
  }

  @Test
  public void deleteById() throws Exception {
    Object id = operRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1", IpType.PUBLIC));

    operRepository.delete(new ID(id));

    List<Operator> got = operRepository.findAll();

    assertTrue(got.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    operRepository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", IpType.PUBLIC));
    operRepository.delete(new ID("id"));

    List<Operator> found = operRepository.findAll();

    assertThat(found.size(), is(1));
  }
}

