package com.clouway.anqp.adapter.persistence;

import org.junit.Test;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Emil Georgiev <emil.georgiev@clouway.com>
 */
public class OperatorEntityTest {
  @Test
  public void happyPath() throws Exception {
    OperatorEntity got = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", "911");
    OperatorEntity want = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", "911");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void nullableEmergencyNumber() throws Exception {
    OperatorEntity got = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", null);
    OperatorEntity want = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", "112");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void emptyEmergencyNumber() throws Exception {
    OperatorEntity got = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", "");
    OperatorEntity want = new OperatorEntity("id", "ACTIVE", "name", "descr", "dName", "fName", "112");

    assertThat(got, deepEquals(want));
  }
}
