package com.clouway.anqp.api.datastore;

import org.bson.Document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 */
public class UpdateStatement {

  public static UpdateStatement update(String name) {
    return new UpdateStatement(name);
  }

  public static UpdateStatement updateBulk(String name) {
    return new UpdateStatement(name, true);
  }

  private final LinkedList<String> stack = new LinkedList<String>();
  private final Map<String, Object> params = new HashMap<String, Object>();

  private boolean bulkUpdate;

  private UpdateStatement(String name) {
    this(name, false);
  }

  private UpdateStatement(String name, boolean bulkUpdate) {
    this.bulkUpdate = bulkUpdate;
    stack.add(name);
  }

  public boolean isForBulkUpdate() {
    return bulkUpdate;
  }

  public UpdateStatement toBe(Object value) {
    params.put(stack.poll(), value);
    return this;
  }

  public UpdateStatement and(String param) {
    stack.add(param);
    return this;
  }

  public Document build() {
    return new Document("$set", params);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UpdateStatement statement = (UpdateStatement) o;

    if (params != null ? !params.equals(statement.params) : statement.params != null) return false;
    if (stack != null ? !stack.equals(statement.stack) : statement.stack != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = stack != null ? stack.hashCode() : 0;
    result = 31 * result + (params != null ? params.hashCode() : 0);
    return result;
  }
}
