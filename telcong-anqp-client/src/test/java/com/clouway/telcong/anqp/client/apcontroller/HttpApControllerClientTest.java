package com.clouway.telcong.anqp.client.apcontroller;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.apcontroller.ApControllerBuilder.apController;
import static com.clouway.telcong.anqp.client.apcontroller.NewApControllerBuilder.newApController;
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
public class HttpApControllerClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public ApControllerClient client = ApControllerClientFactory.create(driver.getBaseUrl());

  @Test
  public void create() throws Exception {
    NewApController apController = newApController().build();
    ClientDriverRequest request = onRequestTo("/r/controllers").withMethod(POST).withBody(json(apController), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.create(apController);
  }

  @Test
  public void findAll() throws Exception {
    List<ApController> aps = newArrayList(apController().build(), apController().build());

    driver.addExpectation(onRequestTo("/r/controllers").withMethod(GET), returnResponse(aps));

    List<ApController> got = client.findAll();

    assertThat(got, deepEquals(aps));
  }

  @Test
  public void findById() throws Exception {
    ApController apController = apController().build();

    driver.addExpectation(onRequestTo("/r/controllers/id").withMethod(GET), returnResponse(apController));

    ApController got = client.findByID("id");

    assertThat(got, deepEquals(apController));
  }

  @Test
  public void update() throws Exception {
    ApController apController = apController().build();
    ClientDriverRequest request = onRequestTo("/r/controllers/id").withMethod(PUT).withBody(json(apController), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.update("id", apController);
  }

  @Test
  public void delete() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/controllers/id").withMethod(DELETE);

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