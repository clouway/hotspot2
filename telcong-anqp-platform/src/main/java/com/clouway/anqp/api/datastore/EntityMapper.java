package com.clouway.anqp.api.datastore;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import org.bson.Document;

import java.io.IOException;

/**
 * EntityMapper is a helper class which maps values stored in object as values stored in json.
 */

final class EntityMapper {
  private final ObjectMapper mapper = new ObjectMapper(new BsonFactory())
          .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
          .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

  public static EntityMapper map() {
    return new EntityMapper();
  }

  EntityMapper() {
  }

  @SuppressWarnings("unchecked")
  public <T> Document fromValue(T object) {
    Document data = null;

    try {
      byte[] bytes = mapper.writeValueAsBytes(object);

      data = mapper.readValue(bytes, Document.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return data;
  }

  @SuppressWarnings("unchecked")
  public <T> T toValue(Document dbObject, Class<T> clazz) {
    try {
      byte[] src = mapper.writeValueAsBytes(dbObject);

      return mapper.readValue(src, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
