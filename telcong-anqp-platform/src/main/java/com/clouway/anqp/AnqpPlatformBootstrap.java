package com.clouway.anqp;

import com.clouway.anqp.adapter.http.HttpModule;
import com.clouway.anqp.adapter.memory.MemoryModule;
import com.clouway.anqp.adapter.persistence.PersistentModule;
import com.clouway.anqp.snmp.SnmpModule;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.bval.guice.ValidationModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * AnqpPlatformBootstrap is a bootstrap class which is representing the entry point of the ANQP platform.
 */
public class AnqpPlatformBootstrap {
  private static final Logger logger = LoggerFactory.getLogger(AnqpPlatformBootstrap.class);
  private static ServiceManager serviceManager;

  private final Integer snmpPort;
  private final Integer httpPort;
  private final String dbHost;

  public AnqpPlatformBootstrap(Integer snmpPort, Integer httpPort, String dbHost) {
    this.snmpPort = snmpPort;
    this.httpPort = httpPort;
    this.dbHost = dbHost;
  }

  public static void main(String[] args) {
    final AnqpPlatformBootstrap app = new AnqpPlatformBootstrap(162, 6158, "dev.telcong.com");

    app.start();

    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      public void run() {
        try {
          app.stop();
        } catch (TimeoutException e) {
          logger.debug("Deadline duruing stopping of the services", e);
        }

        logger.debug("Platform was terminated successfully.");
      }
    }));
  }

  public void start() {
    Thread.currentThread().setName("ANQP");

    logger.debug("Platform was started successfully.");

    Injector injector = Guice.createInjector(
            new CoreModule(),
            new SnmpModule(snmpPort),
            new HttpModule(httpPort),
            new PersistentModule(dbHost),
            new MemoryModule(),
            new ValidationModule()
    );

    serviceManager = injector.getInstance(ServiceManager.class);
    serviceManager.startAsync().awaitHealthy();
  }

  public void stop() throws TimeoutException {
    if (serviceManager != null) {
      serviceManager.stopAsync().awaitStopped(3, TimeUnit.SECONDS);
      return;
    }

    logger.debug("Platform is already stopped.");
  }
}
