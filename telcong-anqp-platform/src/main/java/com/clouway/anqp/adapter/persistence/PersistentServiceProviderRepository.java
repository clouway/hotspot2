package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.EAP.Method;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
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
    return new NewServiceProviderEntity(provider.name, provider.description, adaptToNetwork3GPPEntities(provider.networks), provider.domainNames.values, adaptToConsortiumEntities(provider.consortiums), adaptToNaiEntities(provider.naiRealms));
  }

  private ServiceProviderEntity adapt(ServiceProvider provider) {
    Object id = provider.id.value;

    return new ServiceProviderEntity(id, provider.name, provider.description, adaptToNetwork3GPPEntities(provider.networks), provider.domainNames.values, adaptToConsortiumEntities(provider.consortiums), adaptToNaiEntities(provider.naiRealms));
  }

  private List<Network3GPPEntity> adaptToNetwork3GPPEntities(List<Network3GPP> networks) {
    List<Network3GPPEntity> entities = Lists.newArrayList();

    for (Network3GPP network : networks) {
      entities.add(new Network3GPPEntity(network.name, network.mobileCountryCode, network.mobileNetworkCode));
    }

    return entities;
  }

  private Optional<ServiceProvider> adapt(ServiceProviderEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    DomainNameList domainNames = new DomainNameList(entity.domainNames);

    return Optional.of(new ServiceProvider(new ID(entity._id), entity.name, entity.description, adaptToNetwork3GPPs(entity.networks), domainNames, adaptToConsortiums(entity.consortiums), adaptToNAIs(entity.naiRealms)));
  }

  private List<ServiceProvider> adapt(List<ServiceProviderEntity> entities) {
    List<ServiceProvider> providers = Lists.newArrayList();

    for (ServiceProviderEntity entity : entities) {
      DomainNameList domainNames = new DomainNameList(entity.domainNames);
      providers.add(new ServiceProvider(new ID(entity._id), entity.name, entity.description, adaptToNetwork3GPPs(entity.networks), domainNames, adaptToConsortiums(entity.consortiums), adaptToNAIs(entity.naiRealms)));
    }
    return providers;
  }

  private List<RoamingConsortium> adaptToConsortiums(List<RoamingConsortiumEntity> entities) {
    List<RoamingConsortium> consortiumList = Lists.newArrayList();

    for (RoamingConsortiumEntity entity : entities) {
      consortiumList.add(new RoamingConsortium(entity.name, entity.organizationID));
    }

    return consortiumList;
  }

  private List<Network3GPP> adaptToNetwork3GPPs(List<Network3GPPEntity> entities) {
    List<Network3GPP> networks = Lists.newArrayList();

    for (Network3GPPEntity entity : entities) {
      networks.add(new Network3GPP(entity.name, entity.mobileCountryCode, entity.mobileNetworkCode));
    }

    return networks;
  }

  private List<RoamingConsortiumEntity> adaptToConsortiumEntities(List<RoamingConsortium> consortiumList) {
    List<RoamingConsortiumEntity> entities = Lists.newArrayList();

    for (RoamingConsortium consortium : consortiumList) {
      entities.add(new RoamingConsortiumEntity(consortium.name, consortium.organizationID));
    }

    return entities;
  }

  private List<NaiEntity> adaptToNaiEntities(List<NAI> naiRealms) {
    List<NaiEntity> entities = Lists.newArrayList();

    for (NAI nai : naiRealms) {
      entities.add(new NaiEntity(nai.name, nai.encoding.name(), adaptToEapEntities(nai.eaps)));
    }

    return entities;
  }

  private List<EapEntity> adaptToEapEntities(List<EAP> eaps) {
    List<EapEntity> entities = Lists.newArrayList();

    for (EAP eap : eaps) {
      entities.add(new EapEntity(eap.method.name()));
    }

    return entities;
  }

  private List<NAI> adaptToNAIs(List<NaiEntity> entities) {
    List<NAI> naiList = Lists.newArrayList();

    for (NaiEntity entity : entities) {
      naiList.add(new NAI(entity.name, Encoding.valueOf(entity.encoding), adaptToEAPs(entity.eaps)));
    }

    return naiList;
  }

  private List<EAP> adaptToEAPs(List<EapEntity> entities) {
    List<EAP> eaps = Lists.newArrayList();

    for (EapEntity entity : entities) {
      eaps.add(new EAP(Method.valueOf(entity.method)));
    }

    return eaps;
  }
}
