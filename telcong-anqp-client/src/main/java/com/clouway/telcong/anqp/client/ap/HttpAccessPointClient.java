package com.clouway.telcong.anqp.client.ap;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.clouway.telcong.anqp.client.capability.Capability;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
class HttpAccessPointClient implements AccessPointClient {
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  HttpAccessPointClient(String host) {
    this.host = host;
  }

  @Override
  public void create(NewAccessPoint ap) {
    RequestBody body = RequestBody.create(JSON, json.toJson(ap));
    Request request = new Request.Builder().url(host + "/r/aps").post(body).build();

    client.newCall(request);
  }

  @Override
  public List<AccessPoint> findAll() {
    Request request = new Request.Builder().url(host + "/r/aps").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<AccessPoint>>(){}.getType());
  }

  @Override
  public AccessPoint findByID(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id).build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), AccessPoint.class);
  }

  @Override
  public QueryList findQueryList(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id + "/query-list").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), QueryList.class);
  }

  @Override
  public List<Capability> findCapabilityList(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id + "/capability-list").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<Capability>>(){}.getType());
  }

  @Override
  public String fetchEmergencyAlertURI(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id + "/emergency-uri").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), String.class);
  }

  @Override
  public String fetchLocationURI(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id + "/location-uri").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), String.class);
  }

  @Override
  public ApLocation fetchLocation(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id + "/location").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), ApLocation.class);
  }

  @Override
  public void update(Object id, AccessPointRequest ap) {
    RequestBody body = RequestBody.create(JSON, json.toJson(ap));
    Request request = new Request.Builder().url(host + "/r/aps/" + id).put(body).build();

    client.newCall(request);
  }

  @Override
  public void delete(Object id) {
    Request request = new Request.Builder().url(host + "/r/aps/" + id).delete().build();

    client.newCall(request);
  }
}
