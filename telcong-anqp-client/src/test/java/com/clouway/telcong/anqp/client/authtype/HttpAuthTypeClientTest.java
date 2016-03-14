package com.clouway.telcong.anqp.client.authtype;

import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.authtype.AuthenticationTypeBuilder.newAuthType;
import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpAuthTypeClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public AuthTypeClient client = AuthTypeClientFactory.create(driver.getBaseUrl());

  @Test
  public void findAll() throws Exception {
    List<AuthenticationType> types = newArrayList(newAuthType().build(), newAuthType().build());

    driver.addExpectation(onRequestTo("/r/auth-types").withMethod(GET), returnResponse(types));

    List<AuthenticationType> got = client.findAll();

    assertThat(got, deepEquals(types));
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}