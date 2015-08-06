package com.clouway.anqp.adapter.http;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 *
 */
public class SerialiseJsonMessagesTest {

  static class Person {
    public final String name;
    public final Integer age;

    public Person(String name, Integer age) {
      this.name = name;
      this.age = age;
    }
  }

  static class Device {
    public final String deviceId;
    public final String deviceModel;

    public Device(String deviceId, String deviceModel) {
      this.deviceId = deviceId;
      this.deviceModel = deviceModel;
    }
  }

  @Test
  public void happyPath() throws IOException {
    Person person = new Person("test", 20);

    Person result = serializeAndDeserialize(Person.class, person);

    assertThat(result, is(notNullValue()));
    assertThat(result.name, is(equalTo("test")));
    assertThat(result.age, is(equalTo(20)));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void partialObject() {
    Person person = new Person(null, 20);

    Person result = serializeAndDeserialize(Person.class, person);

    assertThat(result, is(notNullValue()));
    assertThat(result.name, is(nullValue()));
    assertThat(result.age, is(equalTo(20)));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void anotherObject() {

    Device device = new Device("device1", "HotSpot2 Certified");

    Device result = serializeAndDeserialize(Device.class, device);

    assertThat(result, is(notNullValue()));
    assertThat(result.deviceId, is(equalTo("device1")));
    assertThat(result.deviceModel, is(equalTo("HotSpot2 Certified")));
  }

  private <T> T serializeAndDeserialize(Class<T> clazz, T value) {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    GsonTransport transport = new GsonTransport(new Gson());

    try {
      transport.out(bout, clazz, value);

      T existing = transport.in(new ByteArrayInputStream(bout.toByteArray()), clazz);

      return existing;
    } catch (IOException e) {
      e.printStackTrace();

      return null;
    }

  }
}
