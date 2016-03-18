package com.clouway.anqp.adapter.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.rules.ExternalResource;

public final class PersistentDatastoreRule extends ExternalResource {
  private static final String DATABASE_NAME = "anqp_e2e_test";
  private static final Integer port = 27017;

  private MongoDatabase db;

  @Override
  protected void before() throws Throwable {
    MongoClient mongo = new MongoClient("dev.telcong.com", port);

    db = mongo.getDatabase(DATABASE_NAME);
  }

  public String getHostName() {
    return "dev.telcong.com:" + port;
  }

  public String getDatabaseName() {
    return DATABASE_NAME;
  }

  public MongoDatabase db() {
    return db;
  }
}