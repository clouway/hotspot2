package com.clouway.anqp.api.datastore;

import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Datastore is representing an abstraction to the datastore which provides a basic mechanism for saving and retrieving
 * data from the datastore.
 */
@ImplementedBy(MongoDbDatastore.class)
public interface Datastore {

  /**
   * Creates a new index for an entity object.
   *
   * @param entity the entity to which index will be applied
   * @param index  the index that will be used for the applying of the object
   * @param <T>    the type of the entity
   */
  <T> void ensureIndex(Class<T> entity, Index index);

  /**
   * Creates a new index for an entity object.
   *
   * @param nativeName the native name of which index to be created
   * @param index      the index that will be used for the applying of the object
   */
  void ensureIndex(String nativeName, Index index);

  /**
   * Saves the provided entity in the datastore.
   *
   * @param newEntity the entity that need to be saved
   * @param <T>       the type of the entity
   * @return the identifier of the modified object
   */
  <T> Object save(T newEntity);

  /**
   * Finds entity class by providing it's id.
   *
   * @param clazz the type of the entity that need to be found
   * @param id    the id of the entity
   * @param <T>   the type of the entity
   * @return the entity that was persisted by that id or null in case the entity does not exists in the datastore
   */
  <T> T findById(Class<T> clazz, Object id);

  /**
   * Finds all objects of a given type by providing a filter which is used for filtering of the data.
   *
   * @param clazz  the type of the entity that will be retrieved
   * @param filter the filter to be used for filtering
   * @param <T>    the type of the entity
   * @return a list of entities that are matching the provided filter criteria
   */
  <T> List<T> findAllObjectsByFilter(Class<T> clazz, Filter filter);

  /**
   * Finds all objects of a given type.
   *
   * @param clazz the clazz that need to be found
   * @param <T>   the type of the class
   */
  <T> List<T> findAll(Class<T> clazz);

  /**
   * Finds single entity of a given type by providing filter criteria to be used.
   *
   * @param clazz  the clazz to be found
   * @param filter the filter clazz
   * @param <T>    the type of the entity
   * @return the entity that is matching the provided criteria filter
   */
  <T> T findOne(Class<T> clazz, Filter filter);

  /**
   * Delete all entities by providing filter which will be used to match the entities that need to be deleted.
   *
   * @param clazz  the type of the entity that need to be deleted
   * @param filter the filter to be used for the deletion
   * @param <T>    the type of the entity
   */
  <T> void delete(Class<T> clazz, Filter filter);


  /**
   * Deletes the provided entity from the datastore.
   *
   * @param entity the entity that need to be deleted
   * @param <T>    the type of the entity
   */
  <T> void delete(T entity);

  /**
   * Deletes the entity from datastore by id.
   *
   * @param id  the id of the entity that need to be deleted
   * @param <T> the type of the entity
   */
  <T> void deleteById(Class<T> clazz, Object id);

  /**
   * Counts the number of entities of a given type by providing filtering criteria.
   *
   * @param clazz  the target type
   * @param filter the filter
   * @param <T>    the parameter type of the class
   * @return the count of the entities
   */
  <T> Long entityCount(Class<T> clazz, Filter filter);

  /**
   * Counts all entities of a given type.
   *
   * @param clazz the user account type
   * @param <T>   the type of the entity
   * @return the number of entities of a given type
   */
  <T> Long entityCount(Class<T> clazz);

  /**
   * @param entity entity containing new information
   * @param filter filter with criteria to match entity
   * @param <T>    the type of the entity
   */

  <T> void upsert(T entity, Filter filter);

  /**
   * @param clazz     entity containing new information
   * @param filter    filter with criteria to match entity
   * @param statement contains the fields with their new values
   * @param <T>       the type of the entity
   */

  <T> T findAndModify(Class<T> clazz, Filter filter, UpdateStatement statement);

  /**
   * @param clazz     entity containing new information
   * @param filter    filter with criteria to match entity
   * @param statement contains the fields with their new values
   * @param <T>       the type of the entity
   */
  <T> void update(Class<T> clazz, Filter filter, UpdateStatement statement);
}
