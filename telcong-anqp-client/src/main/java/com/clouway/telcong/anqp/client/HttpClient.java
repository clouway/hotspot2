package com.clouway.telcong.anqp.client;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 */
public class HttpClient {
  private final OkHttpClient client = new OkHttpClient();

  public Response newCall(Request request) {
    try {
      Response response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        throw new HttpResponseException(response.code(), response.body().string());
      }

      return response;
    } catch (IOException ioe) {
      throw new CommunicationException();
    }
  }
}
