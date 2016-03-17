package com.clouway.telcong.anqp.client.sp;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.sp.NewServiceProviderBuilder.newProvider;
import static com.clouway.telcong.anqp.client.sp.ServiceProviderBuilder.newServiceProvider;
import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.DELETE;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.POST;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.PUT;
import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpServiceProviderClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public ServiceProviderClient client = ServiceProviderClientFactory.create(driver.getBaseUrl());

  @Test
  public void create() throws Exception {
    NewServiceProvider sp = newProvider().build();
    ClientDriverRequest request = onRequestTo("/r/service-providers").withMethod(POST).withBody(json(sp), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.create(sp);
  }

  @Test
  public void findAll() throws Exception {
    List<ServiceProvider> sps = Lists.newArrayList(newServiceProvider().build(), newServiceProvider().build());

    driver.addExpectation(onRequestTo("/r/service-providers").withMethod(GET), returnResponse(sps));

    List<ServiceProvider> got = client.findAll();

    assertThat(got, deepEquals(sps));
  }

  @Test
  public void findById() throws Exception {
    ServiceProvider sp = newServiceProvider().build();

    driver.addExpectation(onRequestTo("/r/service-providers/id").withMethod(GET), returnResponse(sp));

    ServiceProvider got = client.findByID("id");

    assertThat(got, deepEquals(sp));
  }

  @Test
  public void update() throws Exception {
    ServiceProvider sp = newServiceProvider().build();
    ClientDriverRequest request = onRequestTo("/r/service-providers/id").withMethod(PUT).withBody(json(sp), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.update("id", sp);
  }

  @Test
  public void delete() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/service-providers/id").withMethod(DELETE);

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