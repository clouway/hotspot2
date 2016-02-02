package com.clouway.anqp.adapter.http;

import com.clouway.anqp.service.api.Plugins;
import com.clouway.anqp.service.api.ServicePlugin;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;

/**
 *
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
      }
    });
  }
}
