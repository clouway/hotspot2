package com.clouway.telcong.anqp.client.nai;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpNaiRealmClient implements NaiRealmClient {
  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpNaiRealmClient(String host) {
    this.host = host;
  }

  @Override
  public List<String> fetchEncodings() {
    Request request = new Request.Builder().url(host + "/r/nai-realms/encodings").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<String>>(){}.getType());
  }

  @Override
  public List<String> fetchEapMethods() {
    Request request = new Request.Builder().url(host + "/r/nai-realms/eap-methods").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<String>>(){}.getType());
  }
}
