package com.clouway.anqp.adapter.http;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
class GsonTransport implements Transport {
  private final static Logger logger = LoggerFactory.getLogger("RequestLogger");

  private Gson gson;
  private Provider<HttpServletRequest> requestProvider;

  @Inject
  public GsonTransport(Gson gson, Provider<HttpServletRequest> requestProvider) {
    this.gson = gson;
    this.requestProvider = requestProvider;
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

    logger.debug("INPUT - {} {}", new String(out.toByteArray()), getHeaders());

    return new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), "UTF-8");
  }

  private String getHeaders() {
    HttpServletRequest request = requestProvider.get();
    StringBuilder headers = new StringBuilder();

    Enumeration<String> names = request.getHeaderNames();

    headers.append('\n');

    while (names.hasMoreElements()) {
      String name = names.nextElement();
      String value = request.getHeader(name);

      headers.append(name);
      headers.append(": ");
      headers.append(value);
      headers.append('\n');
    }

    return headers.toString();
  }
}
