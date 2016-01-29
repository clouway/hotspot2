package com.clouway.anqp.api.datastore;

import com.google.common.annotations.VisibleForTesting;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

/**
 * MongoQuery is a query abstraction over the {@link Document} that is used by mongodb for executing queries.
 */
class MongoQuery {
  private final Document example;
  private final Document order;
  private final Integer limit;

  public MongoQuery(Document example, Document order, Integer limit) {
    this.example = example;
    this.order = order;
    this.limit = limit;
  }

  @VisibleForTesting
  protected Document getExample() {
    return example;
  }

  @VisibleForTesting
  protected Document getOrder() {
    return order;
  }

  @VisibleForTesting
  protected Integer getLimit() {
    return limit;
  }


  public Long countIn(MongoCollection<Document> collection) {
    return collection.count(example);
  }

  public MongoCursor<Document> findIn(MongoCollection<Document> collection) {
    FindIterable<Document> query;
    if (order != null) {
      query = collection.find(example).sort(order);
    } else {
      query = collection.find(example);
    }

    if (limit != null) {

      query.limit(limit);
    }

    return query.iterator();
  }


  public void deleteFrom(MongoCollection<Document> collection) {
    collection.deleteMany(example);
  }

  public Document findAndModify(MongoCollection<Document> collection, UpdateStatement statement) {
    return collection.findOneAndUpdate(example, statement.build(), new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
  }

  public void update(MongoCollection<Document> collection, UpdateStatement statement) {
    if (statement.isForBulkUpdate()) {
      collection.updateMany(example, statement.build());
    } else {
      collection.updateOne(example, statement.build());
    }

  }

  @Override
  public String toString() {
    return "Query: " + example + ", order: " + order + ", " + limit;
  }

}
