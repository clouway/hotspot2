package com.clouway.anqp.util.matchers;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static com.clouway.anqp.util.matchers.EqualityMatchers.matching;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 */
public class EqualityMatchersTest {

  interface Elevator {
    void carryAround(Person person);
  }

  class House {
    Person[] people;

    House(Person[] people) {
      this.people = people;
    }
  }

  class Person {
    String name;

    Person(String name) {
      this.name = name;
    }
  }

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Test
  public void equalHouses() throws Exception {
    House houseOne = new House(new Person[]{new Person("Alphonse")});
    House houseTwo = new House(new Person[]{new Person("Alphonse")});

    assertThat(houseOne, deepEquals(houseTwo));
  }

  @Test
  public void differentHouses() throws Exception {
    House houseOne = new House(new Person[]{new Person("Alphonse")});
    House houseTwo = new House(new Person[]{new Person("Gaston")});

    assertThat(houseOne, not(deepEquals(houseTwo)));
  }

  @Test
  public void expectMatchingCall() throws Exception {
    final Elevator elevator = context.mock(Elevator.class);

    context.checking(new Expectations() {{
      oneOf(elevator).carryAround(with(matching(new Person("foo"))));
    }});

    elevator.carryAround(new Person("foo"));
  }

  @Test
  public void expectCallMismatch() {
    final Elevator elevator = context.mock(Elevator.class);

    context.checking(new Expectations() {{
      oneOf(elevator).carryAround(with(not(matching(new Person("bar")))));
    }});

    elevator.carryAround(new Person("foo"));
  }

}
