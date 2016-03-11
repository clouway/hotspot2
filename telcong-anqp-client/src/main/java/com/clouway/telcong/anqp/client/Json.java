package com.clouway.telcong.anqp.client;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 */
public class Json {
  private final Gson gson = new Gson();

  public String toJson(Object src) {
    return gson.toJson(src);
  }

  public <T> T fromJson(InputStream in, Type type) {
    try {
      return gson.fromJson(new InputStreamReader(in, "UTF-8"), type);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new CommunicationException();
    }
  }

  public <T> T fromJson(InputStream in, Class<T> classOfT) {
    try {
       return gson.fromJson(new InputStreamReader(in, "UTF-8"), classOfT);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new CommunicationException();
    }
  }
}
