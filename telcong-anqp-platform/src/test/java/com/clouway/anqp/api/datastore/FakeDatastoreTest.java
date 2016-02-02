package com.clouway.anqp.api.datastore;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 */
public class FakeDatastoreTest extends DatastoreContract {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  @Override
  protected Datastore createDatastore() {
    return new FakeDatastore(datastoreRule.db());
  }
}
