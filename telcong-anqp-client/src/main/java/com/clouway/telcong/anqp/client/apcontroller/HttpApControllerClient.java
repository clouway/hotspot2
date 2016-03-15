package com.clouway.telcong.anqp.client.apcontroller;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpApControllerClient implements ApControllerClient {
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpApControllerClient(String host) {
    this.host = host;
  }

  public void create(NewApController controllerRequest) {
    RequestBody body = RequestBody.create(JSON, json.toJson(controllerRequest));
    Request request = new Request.Builder().url(host + "/r/controllers").post(body).build();

    client.newCall(request);
  }

  public List<ApController> findAll() {
    Request request = new Request.Builder().url(host + "/r/controllers").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<ApController>>(){}.getType());
  }

  public ApController findByID(Object id) {
    Request request = new Request.Builder().url(host + "/r/controllers/" + id).build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), ApController.class);
  }

  public void update(Object id, ApController apController) {
    RequestBody body = RequestBody.create(JSON, json.toJson(apController));
    Request request = new Request.Builder().url(host + "/r/controllers/" + id).put(body).build();

    client.newCall(request);
  }

  public void delete(Object id) {
    Request request = new Request.Builder().url(host + "/r/controllers/" +id).delete().build();

    client.newCall(request);
  }
}
