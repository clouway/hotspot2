package com.clouway.anqp;

import com.clouway.anqp.service.api.ServicePlugin;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.Set;

/**
 * CoreModule is the main module of the ANQP app.
 */
public class CoreModule extends AbstractModule {
  @Override
  protected void configure() {

  }

  @Provides
  @Singleton
  public ServiceManager getServiceManager(Set<ServicePlugin> plugins, Injector injector) {
    Set<Service> services = Sets.newHashSet();
    for (ServicePlugin each : plugins) {
      services.addAll(each.initialize(injector));
    }

    return new ServiceManager(services);
  }
}
