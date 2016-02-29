package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface contains CRUD operation related with {@link com.clouway.anqp.ServiceProvider}.
 */
public interface ServiceProviderRepository {

  /**
   * Creates a new {@link com.clouway.anqp.ServiceProvider}
   *
   * @param provider {@link com.clouway.anqp.ServiceProvider}
   * @return object id
   */
  Object create(NewServiceProvider provider);

  /**
   * Updates a given {@link com.clouway.anqp.ServiceProvider}
   *
   * @param serviceProvider the new {@link com.clouway.anqp.ServiceProvider}
   */
  void update(ServiceProvider serviceProvider);

  /**
   * Finds {@link com.clouway.anqp.ServiceProvider} by id
   *
   * @param id object id
   * @return {@link com.clouway.anqp.ServiceProvider}
   */
  Optional<ServiceProvider> findById(ID id);

  /**
   * Find all the {@link com.clouway.anqp.ServiceProvider}
   *
   * @return list with {@link com.clouway.anqp.ServiceProvider}
   */
  List<ServiceProvider> findAll();

  /**
   * Delete a {@link com.clouway.anqp.ServiceProvider}
   *
   * @param id if the ServiceProvider to be deleted
   */
  void delete(ID id);
}
