package com.clouway.telcong.anqp.client.emergency;

import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.emergency.CapMessageBuilder.newCapMessage;
import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpEmergencyAlertClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public EmergencyAlertClient client = EmergencyAlertClientFactory.create(driver.getBaseUrl());

  @Test
  public void findAll() throws Exception {
    List<CapMessage> messages = newArrayList(newCapMessage().build(), newCapMessage().build());

    driver.addExpectation(onRequestTo("/r/emergency-alerts").withMethod(GET), returnResponse(messages));

    List<CapMessage> got = client.findAll();

    assertThat(got, deepEquals(messages));
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}