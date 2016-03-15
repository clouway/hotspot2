package com.clouway.telcong.anqp.client.ap;

import com.clouway.telcong.anqp.client.capability.Capability;
import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.ap.AccessPointBuilder.accessPoint;
import static com.clouway.telcong.anqp.client.ap.AccessPointRequestBuilder.newAPRequest;
import static com.clouway.telcong.anqp.client.ap.ApLocationBuilder.newLocation;
import static com.clouway.telcong.anqp.client.ap.NewAccessPointBuilder.newAP;
import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.DELETE;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.POST;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.PUT;
import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpAccessPointClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public AccessPointClient client = AccessPointClientFactory.create(driver.getBaseUrl());

  @Test
  public void create() throws Exception {
    NewAccessPoint ap = newAP().build();
    ClientDriverRequest request = onRequestTo("/r/aps").withMethod(POST).withBody(json(ap), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.create(ap);
  }

  @Test
  public void findAll() throws Exception {
    List<AccessPoint> aps = Lists.newArrayList(accessPoint().build(), accessPoint().build());

    driver.addExpectation(onRequestTo("/r/aps").withMethod(GET), returnResponse(aps));

    List<AccessPoint> got = client.findAll();

    assertThat(got, deepEquals(aps));
  }

  @Test
  public void findById() throws Exception {
    AccessPoint ap = accessPoint().build();

    driver.addExpectation(onRequestTo("/r/aps/id").withMethod(GET), returnResponse(ap));

    AccessPoint got = client.findByID("id");

    assertThat(got, deepEquals(ap));
  }

  @Test
  public void findQueryList() throws Exception {
    List<Integer> queries = Lists.newArrayList(256);
    QueryList list = new QueryList(queries);

    driver.addExpectation(onRequestTo("/r/aps/id/query-list").withMethod(GET), returnResponse(list));

    QueryList got = client.findQueryList("id");

    assertThat(got, deepEquals(list));
  }

  @Test
  public void findCapabilityList() throws Exception {
    List<Capability> capabilities = Lists.newArrayList(new Capability(1, "name"));

    driver.addExpectation(onRequestTo("/r/aps/id/capability-list").withMethod(GET), returnResponse(capabilities));

    List<Capability> got = client.findCapabilityList("id");

    assertThat(got, deepEquals(capabilities));
  }

  @Test
  public void fetchEmergencyAlertURI() throws Exception {
    String emergencyURI = "localhost:8080/r/emergency-alert";

    driver.addExpectation(onRequestTo("/r/aps/id/emergency-uri").withMethod(GET), returnResponse(emergencyURI));

    String got = client.fetchEmergencyAlertURI("id");

    assertThat(got, deepEquals(emergencyURI));
  }

  @Test
  public void fetchLocationURI() throws Exception {
    String emergencyURI = "localhost:8080/r/aps/id/location";

    driver.addExpectation(onRequestTo("/r/aps/id/location-uri").withMethod(GET), returnResponse(emergencyURI));

    String got = client.fetchLocationURI("id");

    assertThat(got, deepEquals(emergencyURI));
  }

  @Test
  public void fetchLocation() throws Exception {
    ApLocation location = newLocation().build();

    driver.addExpectation(onRequestTo("/r/aps/id/location").withMethod(GET), returnResponse(location));

    ApLocation got = client.fetchLocation("id");

    assertThat(got, deepEquals(location));
  }

  @Test
  public void update() throws Exception {
    AccessPointRequest ap = newAPRequest().build();
    ClientDriverRequest request = onRequestTo("/r/aps/id").withMethod(PUT).withBody(json(ap), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.update("id", ap);
  }

  @Test
  public void delete() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/aps/id").withMethod(DELETE);

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.delete("id");
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}