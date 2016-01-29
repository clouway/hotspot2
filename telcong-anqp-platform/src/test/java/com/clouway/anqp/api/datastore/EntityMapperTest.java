package com.clouway.anqp.api.datastore;

import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
  public void enumValueIsConvertedToString() {
    Person person = new Person();
    person.status = Status.ACTIVE;

    Document dbObject = EntityMapper.map().fromValue(person);
    assertThat((String) dbObject.get("status"), is("ACTIVE"));
  }

  @Test
  public void nullValuesAreNotAddedToTheObject() {
    Person person = new Person();
    person.firstName = "test1";

    Document dbObject = EntityMapper.map().fromValue(person);

    assertThat(dbObject.keySet(), hasItem("names"));
    assertThat(dbObject.keySet(), hasItem("firstName"));
    assertThat(dbObject.keySet(), not(hasItem("status")));
    assertThat(dbObject.keySet().size(), is(2));
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
