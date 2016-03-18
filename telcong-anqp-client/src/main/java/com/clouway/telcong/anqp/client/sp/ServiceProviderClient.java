package com.clouway.telcong.anqp.client.sp;

import com.clouway.telcong.anqp.client.ID;

import java.util.List;

/**
 */
public interface ServiceProviderClient {
  /**
   * Create new service provider
   *
   * @param provider {@link com.clouway.telcong.anqp.client.sp.NewServiceProvider} which we will created
   */
  ID create(NewServiceProvider provider);

  /**
   * Find all available service providers
   *
   * @return list of {@link ServiceProvider}
   */
  List<ServiceProvider> findAll();

  /**
   * Find service provider by id
   *
   * @param id of the service provider
   *
   * @return {@link ServiceProvider}
   */
  ServiceProvider findByID(Object id);

  /**
   * Update service provider
   *
   * @param provider contains new data for service provider
   */
  void update(Object id, ServiceProvider provider);

  /**
   * Delete service provider
   *
   * @param id of service provider which will be deleted
   */
  void delete(Object id);
}
