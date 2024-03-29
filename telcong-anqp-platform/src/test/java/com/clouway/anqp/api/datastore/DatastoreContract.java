package com.clouway.anqp.api.datastore;

import com.clouway.anqp.util.matchers.EqualityMatchers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.clouway.anqp.api.datastore.Filter.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 */
public abstract class DatastoreContract {

  @Kind("person")
  static class Person {
    @SuppressWarnings("unused")
    private Long _id;
    private String name;
    public int age;

    @SuppressWarnings("unused")
    Person() {

    }

    public Person(Long id, String name) {
      this._id = id;
      this.name = name;
    }

    public Person(Long id, String name, int age) {
      this._id = id;
      this.name = name;
      this.age = age;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Person person = (Person) o;

      if (age != person.age) return false;
      if (_id != null ? !_id.equals(person._id) : person._id != null) return false;
      if (name != null ? !name.equals(person.name) : person.name != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = _id != null ? _id.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + age;
      return result;
    }

    @Override
    public String toString() {
      return "Person{" +
              "id=" + _id +
              ", name='" + name + '\'' +
              ", age=" + age +
              '}';
    }
  }

  @Kind("address")
  static class Address {

    @Id
    @SuppressWarnings("unused")
    private Long _id;

    private String name;

    @SuppressWarnings("unused")
    Address() {

    }

    public Address(Long id, String name) {
      this._id = id;
      this.name = name;
    }
  }

  static class UnmappedClass {
    @SuppressWarnings("unused")
    private String _id;

    @SuppressWarnings("unused")
    private String name;

  }

  @Kind("cclass")
  static class CollectionClass {
    private String _id;
    private List<String> names = new LinkedList<String>();

    public CollectionClass() {
    }

    public CollectionClass(String id, List<String> names) {
      this._id = id;
      this.names = names;
    }

    public String getId() {
      return _id;
    }

    public List<String> getNames() {
      return names;
    }
  }

  private Datastore datastore;

  @Before
  public void initialize() {
    datastore = createDatastore();
  }

  @Test
  public void findExistingEntityById() {
    datastore.save(new Person(10l, "Person1"));

    Person p = datastore.findById(Person.class, 10l);
    assertThat(p.name, is(equalTo("Person1")));
  }

  @Test
  public void findByStringId() {
    CollectionClass c = new CollectionClass();
    c._id = "id1";
    datastore.save(c);

    CollectionClass existing = datastore.findById(CollectionClass.class, "id1");
    assertThat(existing, is(not(nullValue())));
    assertThat(existing._id, is("id1"));
  }

  @Test
  public void nonExistingEntityIsReturnedAsNull() {
    assertThat(datastore.findById(Person.class, 10l), is(nullValue()));
  }

  @Test
  public void loadWhatWasSavedUsingSingleCriteriaFilter() {
    datastore.save(new Person(1l, "test1", 20));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").is("test1"));

    assertThat(personList.size(), is(equalTo(1)));
    assertFirstPersonIs(personList, "test1");
  }

  @Test
  public void multiCriteriaFiltering() {
    datastore.save(new Person(1l, "aa", 10));
    datastore.save(new Person(2l, "aa", 10));
    datastore.save(new Person(3l, "bb", 25));
    datastore.save(new Person(4l, "cc", 30));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").is("aa").and("age").is(10));
    assertThat(personList.size(), is(equalTo(2)));
  }

  @Test
  public void findAllIsReturningAllEntities() {
    datastore.save(new Person(1l, "aa", 10));
    datastore.save(new Person(2l, "aa", 10));
    datastore.save(new Person(3l, "aa", 10));

    List<Person> personList = datastore.findAll(Person.class);
    assertThat(personList.size(), is(3));
  }

  @Test
  public void filterByNullProperty() {
    datastore.save(new Person(5l, null, 10));

    List<Person> personList = datastore.findAllObjectsByFilter(Person.class, where("name").isNullValue().and("age").is(10));
    assertThat(personList.size(), is(equalTo(1)));
  }

  @Test(expected = IllegalStateException.class)
  public void classesThatAreNotUsingKindAndAreNotMappedShouldThrowExceptions() {
    UnmappedClass e1 = new UnmappedClass();
    e1._id = "123";
    e1.name = "test1";

    datastore.save(e1);
  }

  @Test
  public void findSingleEntity() {
    Person person = new Person(1l, "test1");
    datastore.save(person);

    Person existing = datastore.findOne(Person.class, where("name").is("test1"));

    assertThat(existing, is(notNullValue()));
    assertThat(existing.name, is("test1"));
  }

  @Test
  public void nonExistingSingleEntityIsReturnedAsNull() {
    Person existing = datastore.findOne(Person.class, where("name").is("test1"));
    assertThat(existing, is(nullValue()));
  }

  @Test
  public void filtersAreCaseSensitive() {
    datastore.save(new Person(1l, "TEST"));

    List<Person> result = datastore.findAllObjectsByFilter(Person.class, where("name").is("test"));

    assertThat(result.size(), is(0));
  }

  @Test
  public void storeCollectionsOfStrings() {
    CollectionClass c1 = new CollectionClass();
    c1._id = "123";
    c1.names.addAll(Arrays.asList("test1", "test2", "test3"));

    datastore.save(c1);

    CollectionClass expected = datastore.findById(CollectionClass.class, c1._id);
    assertThat(expected._id, is(equalTo(c1._id)));
    assertThat(expected.names, is(equalTo(c1.names)));
  }

  @Test
  public void deleteSingleItem() {
    datastore.save(new Person(1l, "test"));
    datastore.delete(Person.class, where("name").is("test"));

    Person result = datastore.findById(Person.class, 1l);
    assertThat(result, is(nullValue()));
  }

  @Test
  public void deleteItemsWithMatchingFilter() {
    datastore.save(new Person(1l, "abc1"));
    datastore.save(new Person(2l, "abc2"));

    datastore.delete(Person.class, where("name").is("abc2"));

    assertThat(datastore.findById(Person.class, 1l), is(notNullValue()));
    assertThat(datastore.findById(Person.class, 2l), is(nullValue()));
  }

  @Test
  public void deleteExistingEntity() {
    datastore.save(new Person(43l, "abc1"));
    datastore.delete(new Person(43l, "abc1"));

    Person existingPerson = datastore.findById(Person.class, 43l);
    assertThat(existingPerson, is(nullValue()));
  }

  @Test
  public void deleteNonExistingEntity() {
    datastore.delete(new Person(13l, "abc1"));

    Person existingPerson = datastore.findById(Person.class, 43l);
    assertThat(existingPerson, is(nullValue()));
  }

  @Test
  public void deleteEntityById() {
    datastore.save(new Person(43l, "abc1"));
    datastore.deleteById(Person.class, 43l);

    Person existingPerson = datastore.findById(Person.class, 43l);
    assertThat(existingPerson, is(nullValue()));
  }

  @Test
  public void deleteEntityWithUnknownId() {
    Person person = new Person(3333l, "abc1");

    datastore.save(person);
    datastore.deleteById(Person.class, 4444l);

    Person existingPerson = datastore.findById(Person.class, 3333l);
    assertThat(existingPerson, is(person));
  }

  @Test
  public void insertNewEntity() throws Exception {
    Person person = new Person(2l, "John");

    datastore.upsert(person, where("name").is("Patrick"));

    Person existingPerson = datastore.findById(Person.class, 2l);

    assertThat(existingPerson, is(person));
  }

  @Test
  public void updateExistingEntity() throws Exception {
    Person person1 = new Person(2l, "Steven", 36);
    datastore.save(person1);

    Person person2 = new Person(1l, "Steven", 40);

    datastore.upsert(person2, where("name").is(person2.name));

    List<Person> people = datastore.findAll(Person.class);

    assertThat(people.size(), is(1));
    assertThat(people.get(0).age, is(40));
  }

  @Test
  public void updatingOneEntityShouldNotAffectOthers() throws Exception {
    Person john = new Person(1l, "John", 12);
    Person steven = new Person(2l, "Steven", 14);

    datastore.save(john);
    datastore.save(steven);

    steven.age = 15;
    datastore.upsert(steven, where("_id").is(steven._id));

    Person existingPerson = datastore.findById(Person.class, 1l);
    assertThat(existingPerson.age, is(john.age));
  }

  @Test
  public void simpleCount() {
    datastore.save(new Person(1l, "name1"));
    datastore.save(new Address(2l, "name2"));
    datastore.save(new Address(3l, "name2"));

    Long personCount = datastore.entityCount(Person.class);
    Long addressCount = datastore.entityCount(Address.class);

    assertThat(personCount, is(1l));
    assertThat(addressCount, is(2l));
  }

  @Test
  public void countOfSingleEntity() {
    datastore.save(new Person(1l, "name1"));

    Long count = datastore.entityCount(Person.class, where("name").is("name1"));
    assertThat(count, is(1l));
  }

  @Test
  public void countOfManyEntities() {
    datastore.save(new Person(1l, "name1"));
    datastore.save(new Person(2l, "name1"));
    datastore.save(new Person(3l, "name1"));

    Long count = datastore.entityCount(Person.class, where("name").is("name1"));
    assertThat(count, is(3l));
  }

  @Test
  public void countIsUsingTheProvidedFilter() {
    datastore.save(new Person(1l, "name1"));
    datastore.save(new Person(2l, "name2"));
    datastore.save(new Person(3l, "name2"));

    Long count = datastore.entityCount(Person.class, where("name").is("name2"));
    assertThat(count, is(2l));
  }

  @Test
  public void updateWithFilter() {
    datastore.save(new Person(1l, "name1"));
    datastore.save(new Person(2l, "name2"));

    datastore.update(Person.class, where("name").is("name1"), UpdateStatement.update("name").toBe("updated-name"));

    Person updated = datastore.findById(Person.class, 1l);
    Person notUpdated = datastore.findById(Person.class, 2l);
    assertThat(updated.name, is("updated-name"));
    assertThat(notUpdated.name, is("name2"));
  }

  @Test
  public void updateMultipleValues() {
    datastore.save(new Person(1l, "name1"));
    datastore.save(new Person(2l, "name1"));
    datastore.save(new Person(3l, "name2"));

    datastore.update(Person.class, where("name").is("name1"), UpdateStatement.updateBulk("name").toBe("updated-name"));

    Person firstUpdate = datastore.findById(Person.class, 1l);
    Person secondUpdated = datastore.findById(Person.class, 2l);
    Person thirdUpdated = datastore.findById(Person.class, 3l);

    assertThat(firstUpdate.name, is("updated-name"));
    assertThat(secondUpdated.name, is("updated-name"));
    assertThat(thirdUpdated.name, is("name2"));
  }

  @Test
  @Ignore
  public void updateWithCompositeLessThenFilter() {
    datastore.save(new Person(1l, "name1", 10));
    datastore.save(new Person(2l, "name1", 12));
    datastore.save(new Person(3l, "name1", 20));

    datastore.update(Person.class, where("age").isLessThan(15).and("name").is("name1"),
            UpdateStatement.updateBulk("age").toBe(1));

    List<Person> people = datastore.findAll(Person.class);

    Set<Person> actual = Sets.newHashSet(people);
    Set<Person> expected = Sets.newHashSet(
            new Person(1l, "name1", 1),
            new Person(2l, "name1", 1),
            new Person(3l, "name1", 20)
    );

    assertThat(actual, is(expected));
  }

  @Test
  public void usingAllFilter() {
    datastore.save(new CollectionClass("1", Lists.newArrayList("john", "smith")));
    datastore.save(new CollectionClass("2", Lists.newArrayList("john")));
    datastore.save(new CollectionClass("3", Lists.newArrayList("smith")));
    datastore.save(new CollectionClass("4", Lists.newArrayList("john", "smith")));

    List<CollectionClass> result = datastore.findAllObjectsByFilter(CollectionClass.class, where("names").contains(Lists.newArrayList("john", "smith")));
    assertThat(result.size(), is(2));
  }

  @Test
  public void greaterThenFilter() {
    datastore.save(new Person(1l, "name1", 10));
    datastore.save(new Person(2l, "name1", 12));

    List<Person> result = datastore.findAllObjectsByFilter(Person.class, where("age").isGreaterThen(10));
    assertThat(result.size(), is(1));
  }

  @Test
  public void greaterThenById() {
    datastore.save(new Person(1l, "name1", 10));
    datastore.save(new Person(2l, "name1", 12));

    List<Person> result = datastore.findAllObjectsByFilter(Person.class, where("_id").isGreaterThen(1l));
    assertThat(result.size(), is(1));
    assertThat(result.get(0)._id, is(2l));
  }

  protected void assertFirstPersonIs(List<Person> personList, String expectedName) {
    assertThat(personList.get(0).name, is(equalTo(expectedName)));
  }

  protected abstract Datastore createDatastore();
}
