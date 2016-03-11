package com.clouway.telcong.anqp.client;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Rule;
import org.junit.Test;

import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.DELETE;
import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertTrue;

/**
 */
public class HttpClientTest {
  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  private HttpClient client = new HttpClient();
  private String host = driver.getBaseUrl();

  @Test
  public void happyPath() throws Exception {
    Request request = new Request.Builder().url(host + "/endpoint").delete().build();

    ClientDriverRequest driverRequest = onRequestTo("/endpoint").withMethod(DELETE);
    driver.addExpectation(driverRequest, giveEmptyResponse().withStatus(200));

    Response got = client.newCall(request);

    assertTrue(got.isSuccessful());
  }

  @Test(expected = HttpResponseException.class)
  public void sendRequestFailure() throws Exception {
    Request request = new Request.Builder().url(host + "/endpoint").delete().build();

    ClientDriverRequest driverRequest = onRequestTo("/endpoint").withMethod(DELETE);
    driver.addExpectation(driverRequest, giveEmptyResponse().withStatus(500));

    client.newCall(request);
  }

  @Test(expected = CommunicationException.class)
  public void sendRequestWithCommunicationError() throws Exception {
    Request request = new Request.Builder().url("http://localhost:44322").delete().build();

    client.newCall(request);
  }
}