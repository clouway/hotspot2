package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ApControllerRepository;
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
  @Override
  protected void configure() {
    bind(ApControllerRepository.class).to(PersistentApControllerRepository.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  public MongoClient getDatabase() {
    List<ServerAddress> seeds = Lists.newArrayList(new ServerAddress("dev.telcong.com"));

    MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(64).build();

    return new MongoClient(seeds, options);
  }

  @Provides
  @Singleton
  public MongoDatabase getDb(MongoClient instance) {
    return instance.getDatabase("anqp_test");
  }
}