package com.clouway.telcong.anqp.client;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 */
public class JsonTest {

  class Person {
    final String name;
    final Integer age;

    Person(String name, Integer age) {
      this.name = name;
      this.age = age;
    }
  }

  private Json json = new Json();

  @Test
  public void toJson() throws Exception {
    Person person = new Person("Emil", 27);

    String got = json.toJson(person);
    String want = "{\"name\":\"Emil\",\"age\":27}";

    assertThat(got, is(want));
  }

  @Test
  public void fromJSON() throws Exception {
    String response = "{\"name\":\"Emil\",\"age\":27}";
    InputStream in = new ByteArrayInputStream(response.getBytes());

    Person got = json.fromJson(in, Person.class);
    Person want = new Person("Emil", 27);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void fromJSONArray() throws Exception {
    String response = "[{\"name\":\"Emil\",\"age\":27}, {\"name\":\"Ivan\",\"age\":25}]";
    InputStream in = new ByteArrayInputStream(response.getBytes());

    List<Person> got = json.fromJson(in, new TypeToken<List<Person>>(){}.getType());
    List<Person> want = Lists.newArrayList(new Person("Emil", 27), new Person("Ivan", 25));

    assertThat(got, deepEquals(want));
  }
}