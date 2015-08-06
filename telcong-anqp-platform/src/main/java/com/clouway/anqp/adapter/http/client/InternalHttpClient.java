package com.clouway.anqp.adapter.http.client;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 *
 */
class InternalHttpClient implements HttpClient {
  private final OkHttpClient client = new OkHttpClient();

  @Override
  public Response get(String url) {

    Request r = new Request.Builder().url(url).get().build();

    int statusCode = 0;
    String content = null;

    try {
      com.squareup.okhttp.Response response = client.newCall(r).execute();

      statusCode = response.code();
      content = response.body().string();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return new Response(statusCode, content);
  }
}
