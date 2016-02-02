package com.clouway.anqp.api.datastore;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.clouway.anqp.api.datastore.EntityMapper.map;

/**
 * MongoDbDatastore is a basic datastore service implementation that is using MongoDB as persistence
 * layer.
 */
@Singleton
public final class FakeDatastore implements Datastore {
  private final MongoDatabase database;

  @Inject
  public FakeDatastore(MongoDatabase database) {
    this.database = database;
  }

  @Override
  public <T> void ensureIndex(Class<T> entity, Index index) {
    Document nativeIndex = new Document();
    index.apply(nativeIndex);
    database.getCollection(kindOf(entity)).createIndex(nativeIndex, new IndexOptions().background(true));
  }

  @Override
  public void ensureIndex(String nativeName, Index index) {
    Document nativeIndex = new Document();
    index.apply(nativeIndex);
    database.getCollection(nativeName).createIndex(nativeIndex, new IndexOptions().background(true));
  }

  @Override
  public <T> Object save(final T newEntity) {
    Document object = map().fromValue(newEntity);

    if (object.get("_id") != null) {
      Object id = object.get("_id");
      object.remove("_id");

      collection(newEntity)
              .updateOne(new Document("_id", id),
                      new Document("$set", object), new UpdateOptions().upsert(true));
    } else {
      object.append("_id", new ObjectId().toHexString());
      collection(newEntity).insertOne(object);
    }


    return object.get("_id");
  }

  public <T> List<T> findAllObjectsByFilter(Class<T> clazz, Filter filter) {
    MongoQuery query = filter.build();

    return execute(clazz, query);
  }

  public <T> List<T> findAll(Class<T> clazz) {

    String entityKind = kindOf(clazz);


    MongoCursor<Document> cursor = database.getCollection(entityKind).find().iterator();
    return fetch(clazz, cursor);
  }

  public <T> T findOne(Class<T> clazz, Filter filter) {
    List<T> entities = findAllObjectsByFilter(clazz, filter.limit(1));

    if (entities.size() == 0) {
      return null;
    }

    return entities.get(0);
  }

  public <T> void delete(Class<T> clazz, Filter filter) {
    MongoQuery query = filter.build();
    query.deleteFrom(database.getCollection(kindOf(clazz)));
  }

  public <T> void delete(T entity) {
    // REFACTOR We can to retrieve only id value, not the whole set of properties
    Document object = map().fromValue(entity);

    collection(entity).deleteOne(new Document().append("_id", object.get("_id")));
  }

  @Override
  public <T> void deleteById(Class<T> clazz, Object id) {
    database.getCollection(kindOf(clazz)).deleteOne(new Document("_id", id));
  }

  @Override
  public <T> Long entityCount(Class<T> clazz, Filter filter) {
    return filter.build().countIn(database.getCollection(kindOf(clazz)));
  }

  @Override
  public <T> Long entityCount(Class<T> clazz) {
    return database.getCollection(kindOf(clazz)).count();
  }

  @Override
  public <T> void upsert(T entity, Filter filter) {
    Document object = map().fromValue(entity);
    String kind = kindOf(entity.getClass());
    Document updateId = filter.build().getExample();

    database.getCollection(kind).findOneAndReplace(updateId, object, new FindOneAndReplaceOptions().upsert(true));
  }

  @Override
  public <T> T findAndModify(Class<T> clazz, Filter filter, UpdateStatement statement) {
    Document entity = filter.build().findAndModify(database.getCollection(kindOf(clazz)), statement);
    if (entity == null) {
      return null;
    }

    return map().toValue(entity, clazz);
  }

  @Override
  public <T> void update(Class<T> clazz, Filter filter, UpdateStatement statement) {
    filter.build().update(database.getCollection(kindOf(clazz)), statement);
  }

  private <T> List<T> execute(Class<T> clazz, MongoQuery query) {
    String entityKind = kindOf(clazz);

    MongoCursor<Document> cursor = query.findIn(database.getCollection(entityKind));

    return fetch(clazz, cursor);
  }

  private <T> List<T> fetch(Class<T> clazz, MongoCursor<Document> cursor) {
    List<T> entities = new ArrayList<T>();
    while (cursor.hasNext()) {
      Document currentEntity = cursor.next();
      T entity = map().toValue(currentEntity, clazz);
      entities.add(entity);
    }

    return entities;
  }

  private <T> String kindOf(Class<T> clazz) {
    Kind kind = clazz.getAnnotation(Kind.class);

    if (kind == null) {
      throw new IllegalStateException("The class " + clazz + " that is trying to be used by the datastore was not" +
              " annotated by @Kind or was registered as metadata service.");
    }

    return kind.value();
  }

  public <T> T findById(Class<T> clazz, Object id) {

    List<T> entities = execute(clazz, queryById(id));

    if (entities.size() > 0) {
      return entities.get(0);
    }

    return null;
  }

  private <T> MongoCollection<Document> collection(T newEntity) {
    return database.getCollection(kindOf(newEntity.getClass()));
  }

  private MongoQuery queryById(Object id) {
    Document idExample = new Document();
    idExample.put("_id", id);
    return new MongoQuery(idExample, null, 1);
  }
}
