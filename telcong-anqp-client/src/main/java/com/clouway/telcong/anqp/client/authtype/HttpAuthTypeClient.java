package com.clouway.telcong.anqp.client.authtype;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpAuthTypeClient implements AuthTypeClient {
  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpAuthTypeClient(String host) {
    this.host = host;
  }

  @Override
  public List<AuthenticationType> findAll() {
    Request request = new Request.Builder().url(host + "/r/auth-types").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<AuthenticationType>>(){}.getType());
  }
}
