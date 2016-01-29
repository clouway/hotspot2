package com.clouway.anqp.api.datastore;

import com.mongodb.DBObject;
import org.bson.Document;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An index class is representing an single index in the datastore.
 */
public final class Index {

  private final Map<String, Order> indexedProperties = new LinkedHashMap<>();

  public static Index of(String property, Order order) {
    return new Index().indexProperty(property, order);
  }

  private Index() {

  }

  public Index indexProperty(String property, Order order) {
    indexedProperties.put(property, order);
    return this;
  }

  /**
   * Applies index to a {@link DBObject} which is representing the native indexing object in MongoDB.
   *
   * @param object the object to which data will be populated
   */
  protected void apply(Document object) {
    for (String property : indexedProperties.keySet()) {
      Integer value = indexedProperties.get(property).getValue();
      object.put(property, value);
    }
  }


}
