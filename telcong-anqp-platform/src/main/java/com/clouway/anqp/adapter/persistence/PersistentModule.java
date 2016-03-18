package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.AccessPointRepository;
import com.clouway.anqp.ApControllerRepository;
import com.clouway.anqp.OperatorRepository;
import com.clouway.anqp.RoamingGroupRepository;
import com.clouway.anqp.ServiceProviderRepository;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.List;

/**
 */
public class PersistentModule extends AbstractModule {
  private final String host;

  public PersistentModule(String host) {
    this.host = host;
  }

  @Override
  protected void configure() {
    bind(ApControllerRepository.class).to(PersistentApControllerRepository.class).in(Singleton.class);
    bind(AccessPointRepository.class).to(PersistentAccessPointRepository.class).in(Singleton.class);
    bind(RoamingGroupRepository.class).to(PersistentRoamingGroupRepository.class).in(Singleton.class);
    bind(OperatorRepository.class).to(PersistentOperatorRepository.class).in(Singleton.class);
    bind(ServiceProviderRepository.class).to(PersistentServiceProviderRepository.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  public MongoClient getDatabase() {
    List<ServerAddress> seeds = Lists.newArrayList(new ServerAddress(host));

    MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(64).build();

    return new MongoClient(seeds, options);
  }

  @Provides
  @Singleton
  public MongoDatabase getDb(MongoClient instance) {
    return instance.getDatabase("anqp_test");
  }
}