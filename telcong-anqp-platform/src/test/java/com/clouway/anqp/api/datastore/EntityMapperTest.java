package com.clouway.anqp.api.datastore;

import com.google.common.collect.Lists;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class EntityMapperTest {

  static class Person {
    public String firstName;
    public String surname;
    public String family;
    private Date date;
    public List<String> names = new ArrayList<String>();
    public Status status;

    public Person() {
    }

    public Person(String firstName, String surname, String family, Date date, List<String> names, Status status) {
      this.firstName = firstName;
      this.surname = surname;
      this.family = family;
      this.date = date;
      this.names = names;
      this.status = status;
    }
  }

  static class Account {
    public String name;
    public Person person;
    public List<Person> persons;

    public Account() {
    }

    public Account(String name, Person person, List<Person> persons) {
      this.name = name;
      this.person = person;
      this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Account account = (Account) o;

      if (name != null ? !name.equals(account.name) : account.name != null) return false;
      if (person != null ? !person.equals(account.person) : account.person != null) return false;
      return !(persons != null ? !persons.equals(account.persons) : account.persons != null);
    }

    @Override
    public int hashCode() {
      int result = name != null ? name.hashCode() : 0;
      result = 31 * result + (person != null ? person.hashCode() : 0);
      result = 31 * result + (persons != null ? persons.hashCode() : 0);
      return result;
    }
  }

  enum Status {
    ACTIVE, UNKNOWN
  }

  @Test
  public void getWhatWasPut() {
    Person person = new Person();
    person.firstName = "First";
    person.surname = "Surname";
    person.family = "Family";
    person.date = new Date();
    person.names.add("Uncle Faster");

    Document dbObject = EntityMapper.map().fromValue(person);
    assertThat((String) dbObject.get("firstName"), is(equalTo(person.firstName)));

    Person mappedPerson = EntityMapper.map().toValue(dbObject, Person.class);

    assertThat(person.family, is(equalTo(mappedPerson.family)));
    assertThat(mappedPerson.names, hasItem("Uncle Faster"));
    assertThat(mappedPerson.date, is(equalTo(person.date)));
  }

  @Test
  public void getEmbeddedObjects() throws Exception {
    List<String> names = Lists.newArrayList("Ivan", "Stamat");
    Person person = new Person("First", "Surname", "Family", new Date(), names, Status.ACTIVE);

    List<Person> people = Lists.newArrayList(person);
    Account account = new Account("ac", person, people);

    Document dbObject = EntityMapper.map().fromValue(account);
    assertThat((String) dbObject.get("name"), is(equalTo(account.name)));

    Account mappedAccount = EntityMapper.map().toValue(dbObject, Account.class);

    assertThat(mappedAccount,deepEquals(account));
  }

  @Test
  public void enumValueIsConvertedToString() {
    Person person = new Person();
    person.status = Status.ACTIVE;

    Document dbObject = EntityMapper.map().fromValue(person);
    assertThat((String) dbObject.get("status"), is("ACTIVE"));
  }

  @Test
  public void nullValuesAreAddedToTheObject() {
    Person person = new Person();
    person.firstName = "test1";

    Document dbObject = EntityMapper.map().fromValue(person);

    assertThat(dbObject.keySet(), hasItem("names"));
    assertThat(dbObject.keySet(), hasItem("firstName"));
    assertThat(dbObject.keySet(), hasItem("status"));

    assertThat(dbObject.keySet().size(), is(6));
  }

  @Test
  public void stringValueIsConvertedToEnum() {
    Person p = EntityMapper.map().toValue(new Document().append("status", "UNKNOWN"), Person.class);
    assertThat(p.status, is(Status.UNKNOWN));
  }

  @Test
  public void enumFromNullValueIsConvertedAsNullEnum() {
    Person p = EntityMapper.map().toValue(new Document().append("status", null), Person.class);
    assertThat(p.status, is(nullValue()));
  }
}
