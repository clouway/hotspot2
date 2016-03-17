package com.clouway.telcong.anqp.client.rg;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.rg.RoamingGroupBuilder.roamingGroup;
import static com.clouway.telcong.anqp.client.rg.RoamingGroupRequestBuilder.newGroupRequest;
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
public class HttpRoamingGroupClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public RoamingGroupClient client = RoamingGroupClientFactory.create(driver.getBaseUrl());

  @Test
  public void create() throws Exception {
    NewRoamingGroup rg = NewRoamingGroupBuilder.newRG().build();
    ClientDriverRequest request = onRequestTo("/r/roaming-groups").withMethod(POST).withBody(json(rg), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.create(rg);
  }

  @Test
  public void findAll() throws Exception {
    List<RoamingGroup> rgs = newArrayList(roamingGroup().build(), roamingGroup().build());

    driver.addExpectation(onRequestTo("/r/roaming-groups").withMethod(GET), returnResponse(rgs));

    List<RoamingGroup> got = client.findAll();

    assertThat(got, deepEquals(rgs));
  }

  @Test
  public void findById() throws Exception {
    RoamingGroup rg = roamingGroup().build();

    driver.addExpectation(onRequestTo("/r/roaming-groups/id").withMethod(GET), returnResponse(rg));

    RoamingGroup got = client.findByID("id");

    assertThat(got, deepEquals(rg));
  }

  @Test
  public void update() throws Exception {
    RoamingGroupRequest rg = newGroupRequest().build();
    ClientDriverRequest request = onRequestTo("/r/roaming-groups/id").withMethod(PUT).withBody(json(rg), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.update("id", rg);
  }

  @Test
  public void assignOperators() throws Exception {
    List<Object> operIDs = Lists.<Object>newArrayList("1", "2");

    ClientDriverRequest request = onRequestTo("/r/roaming-groups/rgID/operators/assign").withMethod(PUT).withBody(json(operIDs), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.assignOperators("rgID", operIDs);
  }

  @Test
  public void removeOperators() throws Exception {
    List<Object> operIDs = Lists.<Object>newArrayList("1", "2");

    ClientDriverRequest request = onRequestTo("/r/roaming-groups/rgID/operators/remove").withMethod(PUT).withBody(json(operIDs), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.removeOperators("rgID", operIDs);
  }

  @Test
  public void delete() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/roaming-groups/id").withMethod(DELETE);

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