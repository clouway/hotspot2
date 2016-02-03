package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
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

  @Test
  public void findById() throws Exception {
    NewOperator operator = new NewOperator("name", "description", "domainName", "friendlyName");

    Object id = repository.create(operator);

    Operator got = repository.findById(id).get();
    Operator want = new Operator(id, "name", "description", "domainName", "friendlyName");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<Operator> got = repository.findById("id");

    assertFalse(got.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewOperator someOperator = new NewOperator("name1", "description1", "domainName1", "friendlyName1");
    NewOperator anotherOperator = new NewOperator("name2", "description2", "domainName2", "friendlyName2");

    Object id1 = repository.create(someOperator);
    Object id2 = repository.create(anotherOperator);

    List<Operator> got = repository.findAll();
    List<Operator> want = Lists.newArrayList(
            new Operator(id1, "name1", "description1", "domainName1", "friendlyName1"),
            new Operator(id2, "name2", "description2", "domainName2", "friendlyName2")
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewOperator("name", "description", "domainName", "friendlyName"));

    Operator operator = new Operator(id, "newName", "newDescription", "newDomainName", "newFriendlyName");

    repository.update(operator);

    Operator found = repository.findById(id).get();

    assertThat(found, deepEquals(operator));
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewOperator("name", "description", "domainName", "friendlyName"));

    repository.delete(id);

    List<Operator> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewOperator("name", "description", "domainName", "friendlyName"));
    repository.delete("id");

    List<Operator> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}

