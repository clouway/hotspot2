package com.clouway.anqp.api.datastore;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * EntityMapper is a helper class which maps values stored in object as values stored in json.
 */
final class EntityMapper {

  public static EntityMapper map() {
    return new EntityMapper();
  }

  EntityMapper() {

  }

  @SuppressWarnings("unchecked")
  public <T> Document fromValue(T object) {

    Class<?> clazz = object.getClass();
    Field[] fields = clazz.getDeclaredFields();

    Document dbObject = new Document();
    for (Field field : fields) {
      String name = field.getName();


      if (field.getAnnotation(Id.class) != null) {
        // we need to use mongo specific identifier
        name = "_id";
      }

      try {
        field.setAccessible(true);
        Object value = field.get(object);

        if ("_id".equals(name) && value == null) {
          if (field.getType() == Long.class) {
            throw new IllegalStateException("Long primary key was not being set. Long keys should always be set before entity is saved.");
          }

          if (field.getType() == String.class) {
            value = new ObjectId().toString();
          }
        }

        if (value == null) {
          continue;
        }

        if (field.getType().isEnum()) {
          // we should convert enum to proper name
          Enum<?> enumValue = (Enum<?>) value;

          value = enumValue.name();
        }

        dbObject.put(name, value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Property value cannot be retrieved for field: " + field.getName());
      }
    }
    return dbObject;
  }

  @SuppressWarnings("unchecked")
  public <T> T toValue(Document dbObject, Class<T> clazz) {
    try {
      Constructor c = clazz.getDeclaredConstructor();
      c.setAccessible(true);

      Object object = c.newInstance();
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        String propertyName = field.getName();

        if (field.getAnnotation(Id.class) != null) {
          propertyName = "_id";
        }

        Object value = dbObject.get(propertyName);

        if (field.getType().isEnum() && value != null) {
          value = Enum.valueOf((Class<? extends Enum>) field.getType(), (String) value);
        }

        if (field.getType().isAssignableFrom(Set.class) && value != null) {
          value = new HashSet((Collection) value);
        }

        field.set(object, value);
      }
      return (T) object;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

}
