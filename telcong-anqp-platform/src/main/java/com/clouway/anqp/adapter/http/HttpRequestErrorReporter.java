package com.clouway.anqp.adapter.http;

import com.clouway.anqp.core.NotFoundException;
import com.clouway.anqp.core.ProgrammerMistake;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * HttpRequestErrorReporter is a {{@link org.aopalliance.intercept.MethodInterceptor}} class which is reporting errors
 * that are occurring in the sitebrick's {@link com.google.sitebricks.headless.Service} classes.
 * <p/>
 * <p/>
 * For example, when getUserRoles method is invoked and it throws an exception
 * <pre>
 *   {@literal @}Service
 *   {@literal @}At("/r/users")
 *   public class UsersService {
 *      public Reply&lt;?&gt; getUserRoles(Request request) {
 *        UserId userId = request.read(UserId.class).as(Json.class); // fails with exception
 *        ...
 *
 *      }
 *   }
 * </pre>
 * <p/>
 * The {@link com.clouway.anqp.adapter.http.HttpRequestErrorReporter} catches that error and translates it to
 * {@link Errors} objects which is returned as response to the client with
 * the corresponding response code.
 * <p/>
 * <p/>
 * It handles the {@link ValidationException}, by transforming errors into
 * {@link Errors}
 */
 class HttpRequestErrorReporter implements MethodInterceptor {
  private final Logger logger = LoggerFactory.getLogger(HttpRequestErrorReporter.class);

  private final String defaultInternalServerError;

  /**
   * Creates a new HttpRequestErrorReporter by providing the error message to be used when internal server error is occurred.
   *
   * @param defaultInternalServerError
   */
   HttpRequestErrorReporter(String defaultInternalServerError) {
    this.defaultInternalServerError = defaultInternalServerError;
  }

  /**
   * Intercepts the provided MethodInvocation by promoting exception handling in case such ones exist.
   * <p/>
   *
   * @param methodInvocation the methodInvocation to be intercepted.
   * @return the result of the invocation or Reply object that indicate errors if such exists.
   * @throws Throwable in cases when {@link com.clouway.anqp.adapter.http.HttpRequestErrorReporter} is
   *                   not properly bound or when some internal error has occurred.
   */
  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    Object result;

    try {

      result = methodInvocation.proceed();

    } catch (ValidationException e) {

      List<ErrorMessage> messages = Lists.newArrayList();

      for (Error error : e.errors) {
        messages.add(new ErrorMessage(error.message));
      }

      return Reply.with(new Errors(messages)).status(SC_BAD_REQUEST).as(Json.class);

    } catch (ClassCastException e) {

      return Reply.with(new Errors(Lists.newArrayList(new ErrorMessage(defaultInternalServerError)))).status(SC_BAD_REQUEST).as(Json.class);

    } catch (NotFoundException e) {

      return Reply.with(new Errors(Lists.newArrayList(new ErrorMessage(e.getMessage())))).status(SC_NOT_FOUND).as(Json.class);

    } catch (RuntimeException e) {

      logger.error("Exception stack trace", e);

      if(isNullOrEmpty(e.getMessage())) {
        return Reply.with(new Errors(Lists.newArrayList(new ErrorMessage(defaultInternalServerError)))).error().as(Json.class);
      }

      return Reply.with(new Errors(Lists.newArrayList(new ErrorMessage(e.getMessage())))).error().as(Json.class);

    }

    if (!(result instanceof Reply)) {
      throw new ProgrammerMistake("Wrong interceptor binding was performed. It seems that methods that are not returning" +
              " Reply objects are matched.");
    }

    return result;
  }
}