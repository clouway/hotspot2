package com.clouway.anqp.service.api;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Service;
import com.google.inject.Injector;

import java.util.Set;

/**
 * Plugins is a helper class which provides an easy creation of {@link ServicePlugin}s.
 *
 */
public class Plugins {

  /**
   * Constructs a new ServicePlugin for the provided service type.
   *
   * @param clazz the type of the service.
   * @return the newly constructed plugin for the provided service type
   */
  public static <T extends Service> ServicePlugin of(final Class<T> clazz) {
    return new ServicePlugin() {
      @Override
      public Set<Service> initialize(Injector injector) {
        return Sets.<Service>newHashSet(injector.getInstance(clazz));
      }
    };
  }

  /**
   * Constructs a new ServicePlugin for the provided service instance.
   *
   * @param instance the instance of which ServicePlugin to be created
   * @return the newly constructed plugin for the provided service instance
   */
  public static <T extends Service> ServicePlugin of(final T instance) {
    return new ServicePlugin() {
      @Override
      public Set<Service> initialize(Injector injector) {
        return Sets.<Service>newHashSet(instance);
      }
    };
  }

}
