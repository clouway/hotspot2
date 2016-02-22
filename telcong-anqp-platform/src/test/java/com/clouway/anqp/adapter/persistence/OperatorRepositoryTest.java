package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ID;
import com.clouway.anqp.EmergencyNumberException;
import com.clouway.anqp.NewEmergencyNumber;
import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorException;
import com.clouway.anqp.OperatorRepository;
import com.clouway.anqp.OperatorState;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

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

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private OperatorRepository repository = new PersistentOperatorRepository(datastore);

  @Test(expected = OperatorException.class)
  public void createOperatorWithExistingName() throws Exception {
    NewOperator someOperator = new NewOperator("sameName", OperatorState.ACTIVE, "descr", "dName", "fName", "emergency");
    repository.create(someOperator);

    NewOperator anotherOperator = new NewOperator("sameName", OperatorState.ACTIVE, "anotherDescr", "anotherDName", "anotherFName", "anotherEmergencyNumber");
    repository.create(anotherOperator);
  }

  @Test
  public void findById() throws Exception {
    Object id = repository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber"));

    Operator got = repository.findById(new ID(id)).get();
    Operator want = new Operator(new ID(id), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<Operator> got = repository.findById(new ID("id"));

    assertFalse(got.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    Object id1 = repository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1"));
    Object id2 = repository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber2"));

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(id1), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1"),
            new Operator(new ID(id2), "name2", OperatorState.INACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber2")
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "123"));

    Operator operator = new Operator(new ID(id), "newName", OperatorState.INACTIVE, "newDescription", "newDomainName", "newFriendlyName", "*88");

    repository.update(operator);

    Operator found = repository.findById(new ID(id)).get();

    assertThat(found, deepEquals(operator));
  }

  @Test(expected = OperatorException.class)
  public void updateOperatorWithReservedName() throws Exception {
    Object someID = repository.create(new NewOperator("someName", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "123"));
    repository.create(new NewOperator("existName", OperatorState.ACTIVE, "anotherDescription", "anotherDomainName", "anotherFriendlyName", "1234"));

    Operator operator = new Operator(new ID(someID), "existName", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "123");

    repository.update(operator);
  }

  @Test
  public void activate() throws Exception {
    Object someID = repository.create(new NewOperator("name1", OperatorState.INACTIVE, "description1", "dName1", "fName1", "112"));
    Object anotherID = repository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "911"));

    repository.activate(new ID(someID));

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(anotherID), "name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "911"),
            new Operator(new ID(someID), "name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "112")
    );

    assertThat(got, deepEquals(want));
  }

  @Test (expected = NotFoundException.class)
  public void activateUnknownOperator() throws Exception {
    repository.activate(new ID("id"));
  }

  @Test
  public void deactivate() throws Exception {
    Object someID = repository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "112"));
    Object anotherID = repository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "911"));

    repository.deactivate(new ID(someID));

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(anotherID), "name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "911"),
            new Operator(new ID(someID), "name1", OperatorState.INACTIVE, "description1", "dName1", "fName1", "112")
    );

    assertThat(got, deepEquals(want));
  }

  @Test (expected = NotFoundException.class)
  public void deactivateUnknownOperator() throws Exception {
    repository.deactivate(new ID("id"));
  }

  @Test
  public void setEmergencyNumber() throws Exception {
    Object id1 = repository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "911"));
    Object id2 = repository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "1234"));


    NewEmergencyNumber number = new NewEmergencyNumber(new ID(id1), "112");

    repository.updateEmergencyNumber(number);

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(new ID(id2), "name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "1234"),
            new Operator(new ID(id1), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "112")
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = EmergencyNumberException.class)
  public void setAlreadyExistingEmergencyNumber() throws Exception {
    Object id1 = repository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "911"));
    repository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "112"));

    NewEmergencyNumber number = new NewEmergencyNumber(new ID(id1), "112");
    repository.updateEmergencyNumber(number);
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber1"));

    repository.delete(new ID(id));

    List<Operator> got = repository.findAll();

    assertTrue(got.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber"));
    repository.delete(new ID("id"));

    List<Operator> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}

