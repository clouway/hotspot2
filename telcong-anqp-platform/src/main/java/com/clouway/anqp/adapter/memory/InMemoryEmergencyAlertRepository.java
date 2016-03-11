package com.clouway.anqp.adapter.memory;


import com.clouway.anqp.CapMessage;
import com.clouway.anqp.EmergencyAlertRepository;
import com.google.common.collect.ImmutableList;

import java.util.List;

class InMemoryEmergencyAlertRepository implements EmergencyAlertRepository {
  private final List<CapMessage> messages;

  public InMemoryEmergencyAlertRepository(CapMessage... messages) {
    this.messages = ImmutableList.copyOf(messages);
  }

  @Override
  public List<CapMessage> findAll() {
    return messages;
  }
}
