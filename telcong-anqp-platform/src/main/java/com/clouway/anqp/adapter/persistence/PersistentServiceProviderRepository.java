package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ID;
import com.clouway.anqp.NewServiceProvider;
import com.clouway.anqp.ServiceProvider;
import com.clouway.anqp.ServiceProviderException;
import com.clouway.anqp.ServiceProviderRepository;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

/**
 */
class PersistentServiceProviderRepository implements ServiceProviderRepository {
  private final Datastore datastore;

  @Inject
  public PersistentServiceProviderRepository(Datastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public Object create(NewServiceProvider provider) {
    long count = datastore.entityCount(ServiceProviderEntity.class, Filter.where("name").is(provider.name));

    if (count != 0) {
      throw new ServiceProviderException("Duplicate Service Provider Names are not allowed.");
    }

    NewServiceProviderEntity entity = adapt(provider);

    return datastore.save(entity);
  }

  @Override
  public void update(ServiceProvider serviceProvider) {
    long count = datastore.entityCount(ServiceProviderEntity.class, Filter.where("name").is(serviceProvider.name).and("_id").isNot(serviceProvider.id.value));

    if (count != 0) {
      throw new ServiceProviderException("Duplicate Service Provider Names are not allowed.");
    }

    datastore.save(adapt(serviceProvider));
  }

  @Override
  public Optional<ServiceProvider> findById(ID id) {
    ServiceProviderEntity entity = datastore.findOne(ServiceProviderEntity.class, Filter.where("_id").is(id.value));
    return adapt(entity);
  }

  @Override
  public List<ServiceProvider> findAll() {
    List<ServiceProviderEntity> entities = datastore.findAll(ServiceProviderEntity.class);

    return adapt(entities);
  }

  @Override
  public void delete(ID id) {
    datastore.deleteById(ServiceProviderEntity.class, id.value);
  }

  private NewServiceProviderEntity adapt(NewServiceProvider provider) {
    return new NewServiceProviderEntity(provider.name, provider.description);
  }

  private Optional<ServiceProvider> adapt(ServiceProviderEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }
    return Optional.of(new ServiceProvider(new ID(entity._id), entity.name, entity.description));
  }

  private List<ServiceProvider> adapt(List<ServiceProviderEntity> entities) {
    List<ServiceProvider> providers = Lists.newArrayList();

    for (ServiceProviderEntity entity : entities) {
      providers.add(new ServiceProvider(new ID(entity._id), entity.name, entity.description));
    }
    return providers;
  }

  private ServiceProviderEntity adapt(ServiceProvider provider) {
    Object value = provider.id.value;

    return new ServiceProviderEntity(value, provider.name, provider.description);
  }
}
