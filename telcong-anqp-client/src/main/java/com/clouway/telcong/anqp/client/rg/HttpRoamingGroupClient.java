package com.clouway.telcong.anqp.client.rg;

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
public class HttpRoamingGroupClient implements RoamingGroupClient {
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  public HttpRoamingGroupClient(String host) {
    this.host = host;
  }

  @Override
  public void create(NewRoamingGroup group) {
    RequestBody body = RequestBody.create(JSON, json.toJson(group));
    Request request = new Request.Builder().url(host + "/r/roaming-groups").post(body).build();

    client.newCall(request);
  }

  @Override
  public List<RoamingGroup> findAll() {
    Request request = new Request.Builder().url(host + "/r/roaming-groups").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<RoamingGroup>>(){}.getType());
  }

  @Override
  public RoamingGroup findByID(Object id) {
    Request request = new Request.Builder().url(host + "/r/roaming-groups/" + id).build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), RoamingGroup.class);

  }

  @Override
  public void update(Object id, RoamingGroupRequest group) {
    RequestBody body = RequestBody.create(JSON, json.toJson(group));
    Request request = new Request.Builder().url(host + "/r/roaming-groups/" + id).put(body).build();

    client.newCall(request);
  }

  @Override
  public void assignOperators(Object rgID, List<Object> operIDs) {
    RequestBody body = RequestBody.create(JSON, json.toJson(operIDs));
    Request request = new Request.Builder().url(host + "/r/roaming-groups/" + rgID + "/operators/assign").put(body).build();

    client.newCall(request);
  }

  @Override
  public void removeOperators(Object rgID, List<Object> operIDs) {
    RequestBody body = RequestBody.create(JSON, json.toJson(operIDs));
    Request request = new Request.Builder().url(host + "/r/roaming-groups/" + rgID + "/operators/remove").put(body).build();

    client.newCall(request);
  }

  @Override
  public void delete(Object id) {
    Request request = new Request.Builder().url(host + "/r/roaming-groups/" +id).delete().build();

    client.newCall(request);
  }
}
