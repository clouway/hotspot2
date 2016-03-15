package com.clouway.telcong.anqp.client.nai;

import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpNaiRealmClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public NaiRealmClient client = NaiRealmClientFactory.create(driver.getBaseUrl());

  @Test
  public void fetchEncodings() throws Exception {
    List<String> want = Lists.newArrayList("UTF-8", "UTF-16");

    driver.addExpectation(onRequestTo("/r/nai-realms/encodings").withMethod(GET), returnResponse(want));

    List<String> got = client.fetchEncodings();

    assertThat(got, deepEquals(want));
  }

  @Test
  public void fetchEapMethods() throws Exception {
    List<String> want = Lists.newArrayList("EapMethod1", "EapMethod2");

    driver.addExpectation(onRequestTo("/r/nai-realms/eap-methods").withMethod(GET), returnResponse(want));

    List<String> got = client.fetchEapMethods();

    assertThat(got, deepEquals(want));
  }

  @Test
  public void fetchEapAuthentications() throws Exception {
    AuthEntry entry = new AuthEntry("info", Lists.newArrayList("type"));
    List<AuthEntry> want = Lists.newArrayList(entry);

    driver.addExpectation(onRequestTo("/r/nai-realms/auths").withMethod(GET), returnResponse(want));

    List<AuthEntry> got = client.fetchEapAuthentications();

    assertThat(got, deepEquals(want));
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}