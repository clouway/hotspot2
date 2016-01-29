package com.clouway.anqp.api.datastore;

import com.github.fakemongo.Fongo;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.rules.ExternalResource;

/**
 */
public class DatastoreRule extends ExternalResource {
  private static Fongo fongo = new Fongo("Datastore Test");

  private MongoDatabase db;

  @Override
  protected void before() throws Throwable {
    db = fongo.getDatabase("telcong_datastore");
  }

  public MongoDatabase db() {
    return db;
  }

  public boolean containsCollection(String name) {
    ListCollectionsIterable<Document> collections = db.listCollections();

    for (Document current : collections) {
      if (current.getString("name").equals(name)) {
        return true;
      }
    }

    return false;
  }
}