package com.clouway.telcong.anqp.client.operator;

import com.clouway.telcong.anqp.client.HttpClient;
import com.clouway.telcong.anqp.client.Json;
import com.clouway.telcong.anqp.client.ap.AccessPoint;
import com.clouway.telcong.anqp.client.serviceprovider.ServiceProvider;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 */
public class HttpOperatorClient implements OperatorClient {
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpClient client = new HttpClient();
  private final Json json = new Json();
  private final String host;

  public HttpOperatorClient(String host) {
    this.host = host;
  }

  @Override
  public void create(NewOperator operator) {
    RequestBody body = RequestBody.create(JSON, json.toJson(operator));
    Request request = new Request.Builder().url(host + "/r/operators").post(body).build();

    client.newCall(request);
  }

  @Override
  public List<Operator> findAll() {
    Request request = new Request.Builder().url(host + "/r/operators").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<Operator>>(){}.getType());
  }

  @Override
  public Operator findByID(Object id) {
    Request request = new Request.Builder().url(host + "/r/operators/" + id).build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), Operator.class);
  }

  @Override
  public void update(Object id, Operator operator) {
    RequestBody body = RequestBody.create(JSON, json.toJson(operator));
    Request request = new Request.Builder().url(host + "/r/operators/" + id).put(body).build();

    client.newCall(request);
  }

  @Override
  public void assignAccessPoints(Object operID, List<Object> apIDs) {
    RequestBody body = RequestBody.create(JSON, json.toJson(apIDs));
    Request request = new Request.Builder().url(host + "/r/operators/" + operID + "/aps").post(body).build();

    client.newCall(request);
  }

  @Override
  public List<AccessPoint> findAccessPoints(Object id) {
    Request request = new Request.Builder().url(host + "/r/operators/" + id + "/aps").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<AccessPoint>>() {}.getType());
  }

  @Override
  public void assignServiceProviders(Object operID, List<Object> spIDs) {
    RequestBody body = RequestBody.create(JSON, json.toJson(spIDs));
    Request request = new Request.Builder().url(host + "/r/operators/" + operID + "/sps").post(body).build();

    client.newCall(request);
  }

  @Override
  public void removeServiceProviders(Object operID, List<Object> spIDs) {
    RequestBody body = RequestBody.create(JSON, json.toJson(spIDs));
    Request request = new Request.Builder().url(host + "/r/operators/" + operID + "/sps/remove").put(body).build();

    client.newCall(request);
  }

  @Override
  public List<ServiceProvider> findServiceProviders(Object id) {
    Request request = new Request.Builder().url(host + "/r/operators/" + id + "/sps").build();
    Response response = client.newCall(request);

    return json.fromJson(response.body().byteStream(), new TypeToken<List<ServiceProvider>>() {}.getType());
  }


  @Override
  public void activate(Object id) {
    RequestBody emptyBody = RequestBody.create(null, new byte[0]);
    Request request = new Request.Builder().url(host + "/r/operators/" + id + "/activate").post(emptyBody).build();

    client.newCall(request);
  }

  @Override
  public void deactivate(Object id) {
    RequestBody emptyBody = RequestBody.create(null, new byte[0]);
    Request request = new Request.Builder().url(host + "/r/operators/" + id + "/deactivate").post(emptyBody).build();

    client.newCall(request);
  }

  @Override
  public void updateEmergencyNumber(Object id, NewEmergencyNumber emergency) {
    RequestBody body = RequestBody.create(JSON, json.toJson(emergency));
    Request request = new Request.Builder().url(host + "/r/operators/" + id + "/emergency").put(body).build();

    client.newCall(request);
  }

  @Override
  public void delete(Object id) {
    Request request = new Request.Builder().url(host + "/r/operators/" + id).delete().build();

    client.newCall(request);
  }
}
