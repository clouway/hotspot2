package com.clouway.telcong.anqp.client.capability;

import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.capability.CapabilityBuilder.newCapability;
import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertThat;

/**
 */
public class HttpCapabilityClientTest {

  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public CapabilityClient client = CapabilityClientFactory.create(driver.getBaseUrl());

  @Test
  public void findAll() throws Exception {
    List<Capability> capabilities = Lists.newArrayList(newCapability().build(), newCapability().build());

    driver.addExpectation(onRequestTo("/r/capabilities").withMethod(GET), returnResponse(capabilities));

    List<Capability> got = client.findAll();

    assertThat(got, deepEquals(capabilities));
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}