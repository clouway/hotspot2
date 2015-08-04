package com.clouway.anqp.adapter.http;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle.AbstractLifeCycleListener;
import org.eclipse.jetty.util.component.LifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 *
 */
public class HttpBackend extends AbstractIdleService {
  private final Logger log = LoggerFactory.getLogger(HttpBackend.class);
  private final Server server;
  private final Integer port;

  public HttpBackend(Integer port) {
    this.port = port;
    this.server = new Server(port);
  }

  @Override
  protected void startUp() throws Exception {
    ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
    servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

    servletContextHandler.addServlet(DefaultServlet.class, "/");

    server.addLifeCycleListener(new AbstractLifeCycleListener() {
      @Override
      public void lifeCycleStarted(LifeCycle event) {
        log.info("HTTP Backend was started on port: {}", port);
      }

      @Override
      public void lifeCycleStopped(LifeCycle event) {
        log.info("HTTP Backend was stopped.");
      }
    });

    server.start();
  }

  @Override
  protected void shutDown() throws Exception {
    server.stop();
  }
}
