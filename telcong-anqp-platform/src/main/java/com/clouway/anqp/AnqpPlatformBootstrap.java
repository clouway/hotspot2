package com.clouway.anqp;

import com.clouway.anqp.adapter.http.HttpModule;
import com.clouway.anqp.adapter.persistence.PersistentModule;
import com.clouway.anqp.snmp.SnmpModule;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * AnqpPlatformBootstrap is a bootstrap class which is representing the entry point of the ANQP platform.
 */
public class AnqpPlatformBootstrap {
  private static final Logger logger = LoggerFactory.getLogger(AnqpPlatformBootstrap.class);

  public static void main(String[] args) {
    Thread.currentThread().setName("ANQP");

    logger.debug("Platform was started successfully.");

    Injector injector = Guice.createInjector(
            new CoreModule(),
            new SnmpModule(162),
            new HttpModule(8080),
            new PersistentModule()
    );

    final ServiceManager serviceManager = injector.getInstance(ServiceManager.class);
    serviceManager.startAsync().awaitHealthy();

    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      public void run() {
        Thread.currentThread().setName("ANQP");

        try {
          serviceManager.stopAsync().awaitStopped(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
          logger.debug("Deadline duruing stopping of the services", e);
        }

        logger.debug("Platform was terminated successfully.");
      }
    }));
  }
}
