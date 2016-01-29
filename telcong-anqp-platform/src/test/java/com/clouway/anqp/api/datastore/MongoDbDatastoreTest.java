package com.clouway.anqp.api.datastore;

import com.google.common.collect.Lists;
import com.google.inject.Provider;
import com.google.inject.util.Providers;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.api.datastore.Filter.where;
import static com.clouway.anqp.api.datastore.UpdateStatement.update;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 */
public class MongoDbDatastoreTest extends DatastoreContract {

  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private Datastore datastore;

  @Before
  public void initializeDatastore() {
    datastore = createDatastore();
  }

  @Test
  public void multiCriteriaFilteringUsingGreaterThen() {
    datastore.save(new Person(1l, "test2", 10));
    datastore.save(new Person(2l, "testing_user", 25));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").is("testing_user").and("age").isGreaterThen(19));
    assertThat(personList.size(), is(equalTo(1)));
    assertFirstPersonIs(personList, "testing_user");
  }

  @Test
  public void returnedResultsCouldBeLimited() {
    datastore.save(new Person(16l, "test1", 10));
    datastore.save(new Person(17l, "test2", 11));
    datastore.save(new Person(18l, "test3", 12));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("age").isGreaterThen(5).limit(1));
    assertThat(personList.size(), is(equalTo(1)));
  }

  @Test
  public void sortInAscendingOrder() {

    datastore.save(new Person(3l, "named", 20));
    datastore.save(new Person(10l, "named", 15));
    datastore.save(new Person(20l, "named", 35));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").is("named").order("age", Order.ASCENDING));
    assertThat(personList.size(), is(equalTo(3)));

    assertThat(personList.get(0).age, is(equalTo(15)));
    assertThat(personList.get(1).age, is(equalTo(20)));
    assertThat(personList.get(2).age, is(equalTo(35)));
  }

  @Test
  public void sortInDescendingOrder() {
    datastore.save(new Person(13l, "named", 20));
    datastore.save(new Person(14l, "named", 15));
    datastore.save(new Person(15l, "named", 35));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").is("named").order("age", Order.DESCENDING));
    assertThat(personList.size(), is(equalTo(3)));

    assertThat(personList.get(0).age, is(equalTo(35)));
    assertThat(personList.get(1).age, is(equalTo(20)));
    assertThat(personList.get(2).age, is(equalTo(15)));
  }

  @Test
  @Ignore
  public void createDatastoreIndex() {
    Index index = Index.of("property1", Order.DESCENDING);
    datastore.ensureIndex(Person.class, index);

    List<Document> indexInfo = Lists.newArrayList(database().getCollection("person").listIndexes());
    assertThat("index was not created?", indexInfo.size(), is(equalTo(2))); // first one is the key index

    Document indexKey = (Document) indexInfo.get(1).get("key");
    assertThat((Integer) indexKey.get("property1"), is(equalTo(Order.DESCENDING.getValue())));
  }

  @Test
  public void findAndModify() throws Exception {
    datastore.save(new Person(500l, "FeNoMeNa", 24));

    Person expectedPerson = new Person(500l, "Ivan", 24);
    Person actualPerson = datastore.findAndModify(Person.class, where("_id").is(500), update("name").toBe("Ivan"));

    assertThat(actualPerson, is(expectedPerson));
  }

  @Test
  public void findAndModifyNotExistingObject() {
    Person notExistingPerson = datastore.findAndModify(Person.class, where("_id").is(500), update("name").toBe("John"));
    assertThat(notExistingPerson, is(nullValue()));

  }

  @Override
  protected Datastore createDatastore() {
    return new MongoDbDatastore(db());
  }

  private MongoDatabase database() {
    return datastoreRule.db();
  }

  private Provider<MongoDatabase> db() {
    return Providers.of(datastoreRule.db());
  }

}
