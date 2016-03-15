package com.clouway.telcong.anqp.client.operator;

import com.clouway.telcong.anqp.client.ap.AccessPoint;
import com.clouway.telcong.anqp.client.serviceprovider.ServiceProvider;
import com.clouway.telcong.anqp.client.serviceprovider.ServiceProviderBuilder;
import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.ap.AccessPointBuilder.accessPoint;
import static com.clouway.telcong.anqp.client.operator.NewOperatorBuilder.newOperator;
import static com.clouway.telcong.anqp.client.operator.OperatorBuilder.operator;
import static com.clouway.telcong.anqp.client.serviceprovider.ServiceProviderBuilder.newServiceProvider;
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
public class HttpOperatorClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public OperatorClient client = OperatorClientFactory.create(driver.getBaseUrl());

  @Test
  public void create() throws Exception {
    NewOperator oper = newOperator().build();
    ClientDriverRequest request = onRequestTo("/r/operators").withMethod(POST).withBody(json(oper), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.create(oper);
  }

  @Test
  public void findAll() throws Exception {
    List<Operator> want = Lists.newArrayList(operator().build(), operator().build());

    driver.addExpectation(onRequestTo("/r/operators").withMethod(GET), returnResponse(want));

    List<Operator> got = client.findAll();

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findById() throws Exception {
    Operator want = operator().build();

    driver.addExpectation(onRequestTo("/r/operators/id").withMethod(GET), returnResponse(want));

    Operator got = client.findByID("id");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Operator oper = operator().build();
    ClientDriverRequest request = onRequestTo("/r/operators/id").withMethod(PUT).withBody(json(oper), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.update("id", oper);
  }

  @Test
  public void assignAccessPoints() throws Exception {
    List<Object> apIDs = Lists.<Object>newArrayList("apID1", "apID2");
    ClientDriverRequest request = onRequestTo("/r/operators/id/aps").withMethod(POST).withBody(json(apIDs), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.assignAccessPoints("id", apIDs);
  }

  @Test
  public void findAccessPoints() throws Exception {
    List<AccessPoint> want = Lists.newArrayList(accessPoint().build(), accessPoint().build());

    driver.addExpectation(onRequestTo("/r/operators/id/aps").withMethod(GET), returnResponse(want));

    List<AccessPoint> got = client.findAccessPoints("id");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void assignServiceProvider() throws Exception {
    List<Object> spIDs = Lists.<Object>newArrayList("spID1", "spID2");
    ClientDriverRequest request = onRequestTo("/r/operators/id/sps").withMethod(POST).withBody(json(spIDs), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.assignServiceProviders("id", spIDs);
  }

  @Test
  public void removeServiceProviders() throws Exception {
    List<Object> spIDs = Lists.<Object>newArrayList("spID1", "spID2");
    ClientDriverRequest request = onRequestTo("/r/operators/id/sps/remove").withMethod(PUT).withBody(json(spIDs), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.removeServiceProviders("id", spIDs);
  }

  @Test
  public void findServiceProviders() throws Exception {
    List<ServiceProvider> want = Lists.newArrayList(newServiceProvider().build(), newServiceProvider().build());

    driver.addExpectation(onRequestTo("/r/operators/id/sps").withMethod(GET), returnResponse(want));

    List<ServiceProvider> got = client.findServiceProviders("id");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void activate() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/operators/id/activate").withMethod(POST);

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.activate("id");
  }

  @Test
  public void deactivate() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/operators/id/deactivate").withMethod(POST);

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.deactivate("id");
  }

  @Test
  public void updateEmergencyNumber() throws Exception {
    NewEmergencyNumber emergencyNumber = new NewEmergencyNumber("112");

    ClientDriverRequest request = onRequestTo("/r/operators/id/emergency").withMethod(PUT).withBody(json(emergencyNumber), "application/json");

    driver.addExpectation(request, giveEmptyResponse().withStatus(200));

    client.updateEmergencyNumber("id", emergencyNumber);
  }

  @Test
  public void delete() throws Exception {
    ClientDriverRequest request = onRequestTo("/r/operators/id").withMethod(DELETE);

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