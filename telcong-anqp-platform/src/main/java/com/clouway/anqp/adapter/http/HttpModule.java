package com.clouway.anqp.adapter.http;

import com.clouway.anqp.service.api.Plugins;
import com.clouway.anqp.service.api.ServicePlugin;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import org.hibernate.validator.HibernateValidator;

import javax.validation.Validation;
import javax.validation.Validator;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.only;
import static com.google.inject.matcher.Matchers.returns;

/**
 */
public class HttpModule extends AbstractModule {
  private final Integer port;

  public HttpModule(Integer port) {
    this.port = port;
  }

  @Override
  protected void configure() {
    Multibinder<ServicePlugin> plugins = Multibinder.newSetBinder(binder(), ServicePlugin.class);
    plugins.addBinding().toInstance(Plugins.of(new HttpBackend(port)));
    bind(ObjectValidator.class).to(HibernateObjectValidator.class).in(Singleton.class);

    install(new ServletModule() {
      @Override
      protected void configureServlets() {
        bind(TestServlet.class).in(Singleton.class);
        serve("/test").with(TestServlet.class);
      }
    });

    install(new SitebricksModule() {
      @Override
      protected void configureSitebricks() {
        at("/testservice").serve(ExampleService.class);

        at("/r/controllers").serve(ApControllerService.class);
        at("/r/aps").serve(AccessPointService.class);
        at("/r/operators").serve(OperatorService.class);
        at("/r/roaming-groups").serve(RoamingGroupService.class);
      }
    });

    bindInterceptor(annotatedWith(Service.class), returns(only(Reply.class)), new HttpRequestErrorReporter("Internal Server Error"));
  }

  @Provides
  @Singleton
  public Validator getValidator() {
    return Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator();
  }
}
