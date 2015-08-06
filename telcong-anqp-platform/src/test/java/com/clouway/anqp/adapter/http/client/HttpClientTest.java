package com.clouway.anqp.adapter.http.client;

import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 */
public class HttpClientTest {

  @Rule
  public ClientDriverRule driver = new ClientDriverRule();

  @Test
  public void happyPath() {
    HttpClient client = new InternalHttpClient();
    driver.addExpectation(onRequestTo("/blah"), giveEmptyResponse().withStatus(200));

    Response response = client.get(driver.getBaseUrl() + "/blah");
    assertThat(response.code(), is(equalTo(200)));
  }


  @Test
  public void notFound() {
    HttpClient client = new InternalHttpClient();
    driver.addExpectation(onRequestTo("/blah"), giveEmptyResponse().withStatus(404));

    Response response = client.get(driver.getBaseUrl() + "/blah");
    assertThat(response.code(), is(equalTo(404)));
  }

  @Test
  public void badRequest() {
    HttpClient client = new InternalHttpClient();
    driver.addExpectation(onRequestTo("/blah"), giveEmptyResponse().withStatus(401));

    Response response = client.get(driver.getBaseUrl() + "/blah");
    assertThat(response.code(), is(equalTo(401)));
  }

  @Test
  public void contentIsReceived() {
    HttpClient client = new InternalHttpClient();
    driver.addExpectation(onRequestTo("/blah"), giveResponse("test", "text").withStatus(200));

    Response response = client.get(driver.getBaseUrl() + "/blah");
    assertThat(response.code(), is(equalTo(200)));
    assertThat(response.content(), is("test"));
  }
}
