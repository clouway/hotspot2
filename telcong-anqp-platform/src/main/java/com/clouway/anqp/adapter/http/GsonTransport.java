package com.clouway.anqp.adapter.http;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 */
class GsonTransport implements Transport {
  private final static Logger logger = LoggerFactory.getLogger("RequestLogger");

  private final Gson gson;

  @Inject
  public GsonTransport(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> T in(InputStream input, Class<T> tClass) throws IOException {
    T entity = gson.fromJson(createReader(input), tClass);
    return entity;
  }

  @Override
  public <T> T in(InputStream input, TypeLiteral<T> typeLiteral) throws IOException {
    T entity = gson.fromJson(createReader(input), typeLiteral.getType());

    return entity;
  }

  @Override
  public <T> void out(OutputStream outputStream, Class<T> tClass, T t) throws IOException {
    String json = gson.toJson(t);

    outputStream.write(json.getBytes("UTF8"));
  }

  @Override
  public String contentType() {
    return "application/json";
  }

  private InputStreamReader createReader(InputStream input) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteStreams.copy(input, out);

    logger.debug("INPUT - {}", new String(out.toByteArray()));

    return new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), "UTF-8");
  }
}
