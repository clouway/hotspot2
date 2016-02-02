package com.clouway.anqp.adapter.http;

import com.google.common.collect.Multimap;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;
import com.google.sitebricks.headless.Request;

import java.io.IOException;
import java.io.OutputStream;

/**
 */
class RequestFactory<T> implements Request {

  public static <T> RequestFactory<T> makeRequestThatContains(T value) {
    return new RequestFactory<T>(value);
  }

  private final T value;

  public RequestFactory(T value) {
    this.value = value;
  }

  @Override
  public <T> RequestRead<T> read(Class<T> eClass) {
    return new RequestRead<T>() {
      @Override
      public T as(Class<? extends Transport> aClass) {
        return (T) value;
      }
    };
  }

  @Override
  public <E> RequestRead<E> read(TypeLiteral<E> typeLiteral) {
    return new RequestRead<E>() {
      @Override
      public E as(Class<? extends Transport> transport) {
        return (E) value;
      }
    };
  }

  @Override
  public void readTo(OutputStream outputStream) throws IOException {
  }

  @Override
  public Multimap<String, String> headers() {
    return null;
  }

  @Override
  public Multimap<String, String> params() {
    return null;
  }

  @Override
  public Multimap<String, String> matrix() {
    return null;
  }

  @Override
  public String matrixParam(String s) {
    return null;
  }

  @Override
  public String param(String s) {
    return null;
  }

  @Override
  public String header(String s) {
    return null;
  }

  @Override
  public String uri() {
    return null;
  }

  @Override
  public String path() {
    return null;
  }

  @Override
  public String context() {
    return null;
  }

  @Override
  public String method() {
    return null;
  }
}
