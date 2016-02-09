package com.clouway.anqp.adapter.http;

import com.clouway.anqp.core.NotFoundException;
import com.clouway.anqp.core.ProgrammerMistake;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.contains;
import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isBadRequest;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isInternalServerError;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isNotFound;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.only;
import static com.google.inject.matcher.Matchers.returns;
import static org.junit.Assert.*;

/**
 */
public class HttpRequestErrorReporterTest {

  private static final String onMissingDevice = "cpe cannot be found";

  @Service
  static class MyService {

    @Get
    public Reply<?> echo(String value) {
      return Reply.with(value).ok();
    }

    @Post
    public Reply<?> failingMethod() {
      throw new RuntimeException();
    }

    @Post
    public Reply<?> customException() {
      throw new NotFoundException(onMissingDevice);
    }

    @Post
    public Reply<?> validationExceptions(List<Error> errors) {
      throw new ValidationException(errors);
    }

    @Post
    public Reply<?> classCastException() {
      throw new ClassCastException();
    }

    public String methodThatDoesNotReturnReply() {
      return "bla";
    }
  }

  @Test
  public void happyPath() throws IOException, ServletException {
    MyService service = newService();
    Reply<?> reply = service.echo("test");

    assertThat(reply, isOk());
    assertThat(reply, containsValue("test"));
  }


  @Test
  public void serviceFailsWithCustomException() throws Exception {
    MyService service = newService();
    Reply<?> reply = service.customException();
    assertThat(reply, isNotFound());

    assertThat(reply, contains(new Errors(Lists.newArrayList(new ErrorMessage(onMissingDevice)))));
  }

  @Test
  public void serverFailsWithGenericException() throws IOException, ServletException {
    Reply<?> reply = newService().failingMethod();
    assertThat(reply, isInternalServerError());

    assertThat(reply, contains(anyServerError()));
  }

  @Test
  public void serverFailsWithValidationErrors() {
    Reply<?> reply = newService().validationExceptions(Lists.newArrayList(
                    new Error("error1"), new Error("error2"))
    );

    assertThat(reply,isBadRequest());
    assertThat(reply, contains(new Errors(Lists.newArrayList(
            new ErrorMessage("error1"),
            new ErrorMessage("error2")
    ))));
  }

  @Test
  public void serverCannotCastTheProvidedRequest() {
    Reply<?> reply = newService().classCastException();

    assertThat(reply, isBadRequest());
    assertThat(reply, contains(anyServerError()));
  }

  @Test
  public void wrongMatchersWhereUsedForBinding() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bindInterceptor(annotatedWith(Service.class), returns(only(String.class)), new HttpRequestErrorReporter(""));
      }
    });

    try {
      injector.getInstance(MyService.class).methodThatDoesNotReturnReply();
      fail("Exception was not thrown when interceptor matchers where incorrect?");
    } catch (ProgrammerMistake e) { }

  }

  private Errors anyServerError() {
    return new Errors(Lists.newArrayList(new ErrorMessage("Internal Server Error")));
  }

  private MyService newService() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bindInterceptor(annotatedWith(Service.class), returns(only(Reply.class)), new HttpRequestErrorReporter("Internal Server Error"));
      }
    });

    return injector.getInstance(MyService.class);
  }
}
