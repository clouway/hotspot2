package com.clouway.anqp.snmp;

import com.clouway.anqp.service.api.ServicePlugin;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

import java.util.Set;

/**
 */
public class SnmpModule extends AbstractModule {
  private final Integer port;

  public SnmpModule(Integer port) {
    this.port = port;
  }

  @Override
  protected void configure() {
    bind(SnmpMessageListener.class).to(SimpleSnmpMessageListener.class);

    Multibinder<ServicePlugin> plugins = Multibinder.newSetBinder(binder(), ServicePlugin.class);
    plugins.addBinding().toInstance(new ServicePlugin() {
      @Override
      public Set<Service> initialize(Injector injector) {
        MibRepository mibRepository = injector.getInstance(MibRepository.class);
        SnmpMessageListener messageListener = injector.getInstance(SnmpMessageListener.class);

        return Sets.<Service>newHashSet(new SnmpMessagesDaemon(mibRepository, messageListener, port));
      }
    });
  }
}