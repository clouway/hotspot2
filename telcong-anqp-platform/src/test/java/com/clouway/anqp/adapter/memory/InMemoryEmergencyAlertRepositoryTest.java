package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.CapMessage;
import com.clouway.anqp.EmergencyAlertRepository;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.CapMessageBuilder.newCapMessage;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.junit.Assert.assertThat;

public class InMemoryEmergencyAlertRepositoryTest {
  private CapMessage message = newCapMessage().build();
  private EmergencyAlertRepository repository = new InMemoryEmergencyAlertRepository(message);

  @Test
  public void fetchAll() throws Exception {
    List<CapMessage> actual = repository.findAll();
    List<CapMessage> expected = Lists.newArrayList(message);

    assertThat(actual, deepEquals(expected));
  }
}