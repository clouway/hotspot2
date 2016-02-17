package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.EmergencyNumberException;
import com.clouway.anqp.NewEmergencyNumber;
import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorException;
import com.clouway.anqp.OperatorRepository;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
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
    NewOperator someOperator = new NewOperator("sameName", "descr", "dName", "fName", "emergency");
    repository.create(someOperator);

    NewOperator anotherOperator = new NewOperator("sameName", "anotherDescr", "anotherDName", "anotherFName", "anotherEmergencyNumber");
    repository.create(anotherOperator);
  }

  @Test
  public void findById() throws Exception {
    NewOperator operator = new NewOperator("name", "description", "domainName", "friendlyName", "emergencyNumber");

    Object id = repository.create(operator);

    Operator got = repository.findById(id).get();
    Operator want = new Operator(id, "name", "description", "domainName", "friendlyName", "emergencyNumber");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<Operator> got = repository.findById("id");

    assertFalse(got.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewOperator someOperator = new NewOperator("name1", "description1", "domainName1", "friendlyName1", "emergencyNumber");
    NewOperator anotherOperator = new NewOperator("name2", "description2", "domainName2", "friendlyName2", "emergencyNumber");

    Object id1 = repository.create(someOperator);
    Object id2 = repository.create(anotherOperator);

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(id1, "name1", "description1", "domainName1", "friendlyName1", "emergencyNumber"),
            new Operator(id2, "name2", "description2", "domainName2", "friendlyName2", "emergencyNumber")
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewOperator("oldName", "description", "domainName", "friendlyName", "123"));

    Operator operator = new Operator(id, "oldName", "newDescription", "newDomainName", "newFriendlyName", "*88");

    repository.update(operator);

    Operator found = repository.findById(id).get();

    assertThat(found, deepEquals(operator));
  }

  @Test
  public void updateOperatorName() throws Exception {
    Object id = repository.create(new NewOperator("name", "description", "domainName", "friendlyName", "123"));

    Operator operator = new Operator(id, "newName", "description", "domainName", "friendlyName", "123");

    repository.update(operator);

    Operator found = repository.findById(id).get();

    assertThat(found, deepEquals(operator));
  }

  @Test(expected = OperatorException.class)
  public void updateOperatorWithReservedName() throws Exception {
    Object someID = repository.create(new NewOperator("someName", "description", "domainName", "friendlyName", "123"));
    repository.create(new NewOperator("existName", "anotherDescription", "anotherDomainName", "anotherFriendlyName", "1234"));

    Operator operator = new Operator(someID, "existName", "newDescription", "newDomainName", "newFriendlyName", "*88");

    repository.update(operator);
  }

  @Test
  public void setEmergencyNumber() throws Exception {
    Object id1 = repository.create(new NewOperator("name1", "description1", "domainName1", "friendlyName1", "911"));
    Object id2 = repository.create(new NewOperator("name2", "description2", "domainName2", "friendlyName2", "1234"));

    NewEmergencyNumber number = new NewEmergencyNumber(id1, "112");

    repository.updateEmergencyNumber(number);

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(id2, "name2", "description2", "domainName2", "friendlyName2", "1234"),
            new Operator(id1, "name1", "description1", "domainName1", "friendlyName1", "112")
    );

    assertThat(got, deepEquals(want));
  }

  @Test(expected = EmergencyNumberException.class)
  public void setAlreadyExistingEmergencyNumber() throws Exception {
    Object id1 = repository.create(new NewOperator("name1", "description1", "domainName1", "friendlyName1", "911"));
    repository.create(new NewOperator("name2", "description2", "domainName2", "friendlyName2", "112"));

    NewEmergencyNumber number = new NewEmergencyNumber(id1, "112");
    repository.updateEmergencyNumber(number);
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewOperator("name", "description", "domainName", "friendlyName", "emergencyNumber"));

    repository.delete(id);

    List<Operator> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewOperator("name", "description", "domainName", "friendlyName", "emergencyNumber"));
    repository.delete("id");

    List<Operator> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}

