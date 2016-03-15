package com.clouway.telcong.anqp.client.serviceprovider;

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
class HttpServiceProviderClient implements ServiceProviderClient {
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpServiceProviderClient(String host) {
    this.host = host;
  }

  @Override
  public void create(NewServiceProvider provider) {
    RequestBody body = RequestBody.create(JSON, json.toJson(provider));
    Request request = new Request.Builder().url(host + "/r/service-providers").post(body).build();

    client.newCall(request);
  }

  @Override
  public List<ServiceProvider> findAll() {
    Request request = new Request.Builder().url(host + "/r/service-providers").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<ServiceProvider>>(){}.getType());
  }

  @Override
  public ServiceProvider findByID(Object id) {
    Request request = new Request.Builder().url(host + "/r/service-providers/" + id).build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), ServiceProvider.class);
  }

  @Override
  public void update(Object id, ServiceProvider provider) {
    RequestBody body = RequestBody.create(JSON, json.toJson(provider));
    Request request = new Request.Builder().url(host + "/r/service-providers/" + id).put(body).build();

    client.newCall(request);
  }

  @Override
  public void delete(Object id) {
    Request request = new Request.Builder().url(host + "/r/service-providers/" + id).delete().build();

    client.newCall(request);
  }
}
