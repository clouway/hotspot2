package com.clouway.telcong.anqp.client.capability;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpCapabilityClient implements CapabilityClient {
  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpCapabilityClient(String host) {
    this.host = host;
  }

  @Override
  public List<Capability> findAll() {
    Request request = new Request.Builder().url(host + "/r/capabilities").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<Capability>>(){}.getType());
  }
}
