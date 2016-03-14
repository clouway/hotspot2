package com.clouway.telcong.anqp.client.emergency;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpEmergencyAlertClient implements EmergencyAlertClient {
  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpEmergencyAlertClient(String host) {
    this.host = host;
  }

  @Override
  public List<CapMessage> findAll() {
    Request request = new Request.Builder().url(host + "/r/emergency-alerts").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<CapMessage>>(){}.getType());
  }
}
