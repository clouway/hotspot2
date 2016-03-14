package com.clouway.telcong.anqp.client.venueitem;

import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.telcong.anqp.client.util.matchers.EqualityMatchers.deepEquals;
import static com.clouway.telcong.anqp.client.venueitem.VenueItemBuilder.newVenueItem;
import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class HttpVenueItemClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  public VenueItemClient client = VenueItemClientFactory.create(driver.getBaseUrl());

  @Test
  public void findAll() throws Exception {
    List<VenueItem> items = newArrayList(newVenueItem().build(), newVenueItem().build());

    driver.addExpectation(onRequestTo("/r/venues").withMethod(GET), returnResponse(items));

    List<VenueItem> got = client.findAll();

    assertThat(got, deepEquals(items));
  }

  private ClientDriverResponse returnResponse(Object object) {
    return giveResponse(json(object), "application/json; charset=utf-8");
  }

  private String json(Object object) {
    return new Gson().toJson(object);
  }
}