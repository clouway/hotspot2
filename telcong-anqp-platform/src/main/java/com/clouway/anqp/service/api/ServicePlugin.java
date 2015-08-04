package com.clouway.anqp.service.api;


import com.google.common.util.concurrent.Service;
import com.google.inject.Injector;

import java.util.Set;

/**
 * ServicePlugin is a plugin which is used for the initialization of all services that
 * are provided in the AAA.
 * <p/>
 * It is used in combination with Guice's {@link com.google.inject.multibindings.Multibinder} to provide plug-in functionality
 * to the AAA.
 * <p/>
 * Here is an example usage of it:
 *
 * <pre>
 *   Multibinder&lt;ServicePlugin&gt; plugins
 *            = Multibinder.newSetBinder(binder(), ServicePlugin.class);
 *    plugins.addBinding().toInstance(Plugins.of(KeepAliveService.class));
 *
 * </pre>
 * <p/>
 *
 * To provide an easy usage, package contains and helper class {@link Plugins} which is simplifying
 * the creation of new {@link com.clouway.anqp.service.api.ServicePlugin} for a single {@link com.google.common.util.concurrent.Service}.

 */
public interface ServicePlugin {

  /**
   * Creates a new service by providing the injector which to be used as dependency.
   * @param injector the injector to be provided.
   * @return the newly created service.
   */
  Set<Service> initialize(Injector injector);

}
