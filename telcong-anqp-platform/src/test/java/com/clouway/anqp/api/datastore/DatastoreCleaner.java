package com.clouway.anqp.api.datastore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.rules.ExternalResource;

/**
 */
public class DatastoreCleaner extends ExternalResource {
  private MongoDatabase db;

  public DatastoreCleaner(MongoDatabase db) {
    this.db = db;
  }

  @Override
  protected void before() throws Throwable {
    // we need to be sure that all existing collections are cleared, so we are starting from clean state.
    for (Document collectionEntity : db.listCollections()) {
      String collectionName = collectionEntity.getString("name");

      if (collectionName.contains("system.")) {
        continue;
      }

      MongoCollection<Document> collection = db.getCollection(collectionName);
      collection.deleteMany(new Document());
    }
  }
}