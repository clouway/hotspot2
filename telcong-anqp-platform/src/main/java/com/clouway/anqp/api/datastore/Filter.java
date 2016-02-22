package com.clouway.anqp.api.datastore;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A Filter class which is used to express filtered operation that could be performed in the datastore.
 */
public final class Filter {

  private final Queue<String> queue = new LinkedList<String>();
  private final Document filter = new Document();
  private Document order;
  private Integer limit;

  public static Filter where(String property) {
    return new Filter(property);
  }

  public Filter(final String property) {
    queue.add(property);
  }

  public Filter is(Object propertyValue) {
    filter.put(queue.poll(), propertyValue);
    return this;
  }

  public Filter is(String propertyValue) {
    filter.put(queue.poll(), propertyValue);
    return this;
  }

  public Filter is(Boolean propertyValue) {
    filter.put(queue.poll(), propertyValue);
    return this;
  }

  public Filter is(Integer propertyValue) {
    filter.put(queue.poll(), propertyValue);
    return this;
  }

  public Filter contains(List<String> values) {
    filter.put(queue.poll(), condition("$all", values));
    return this;
  }

  public Filter in(List<Object> values) {
    filter.put(queue.poll(), condition("$in", values));
    return this;
  }

  public Filter is(Long propertyValue) {
    filter.put(queue.poll(), propertyValue);
    return this;
  }

  public Filter and(String property) {
    queue.add(property);
    return this;
  }

  public Filter isNullValue() {
    filter.put(queue.poll(), null);
    return this;
  }

  public Filter isNot(Object value) {
    String property = queue.poll();
    filter.put(property, condition("$ne", value));
    return this;
  }

  public Filter isGreaterThen(Integer value) {
    String property = queue.poll();
    filter.put(property, condition("$gt", value));
    return this;
  }

  public Filter isGreaterThen(Long value) {
    String property = queue.poll();
    filter.put(property, condition("$gt", value));
    return this;
  }

  public Filter isGreaterThan(String value) {
    String property = queue.poll();
    filter.put(property, condition("$gt", value));
    return this;
  }

  public Filter isLessThan(String value) {
    String property = queue.poll();
    filter.put(property, condition("$lt", value));
    return this;
  }

  public Filter isLessThan(Integer value) {
    String property = queue.poll();
    filter.put(property, condition("$lt", value));
    return this;
  }

  public Filter isLessThan(Date value) {
    String property = queue.poll();
    filter.put(property, condition("$lt", value));
    return this;
  }

  public Filter matches(String value) {
    String property = queue.poll();
    filter.put(property, new BasicDBObject().append("$regex", value).append("$options", "i"));
    return this;
  }

  private DBObject condition(String conditionName, Object value) {
    return new BasicDBObject(conditionName, value);
  }

  public Filter order(String propertyName, Order ordering) {
    if (this.order == null) {
      order = new Document();
    }

    order.put(propertyName, ordering.getValue());
    return this;
  }

  public Filter limit(int limit) {
    this.limit = limit;
    return this;
  }

  protected MongoQuery build() {
    return new MongoQuery(filter, order, limit);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Filter filter1 = (Filter) o;

    if (filter != null ? !filter.equals(filter1.filter) : filter1.filter != null) return false;
    if (limit != null ? !limit.equals(filter1.limit) : filter1.limit != null) return false;
    if (order != null ? !order.equals(filter1.order) : filter1.order != null) return false;
    if (queue != null ? !queue.equals(filter1.queue) : filter1.queue != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = queue != null ? queue.hashCode() : 0;
    result = 31 * result + (filter != null ? filter.hashCode() : 0);
    result = 31 * result + (order != null ? order.hashCode() : 0);
    result = 31 * result + (limit != null ? limit.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Filter{" +
            "filter=" + filter +
            ", order=" + order +
            ", limit=" + limit +
            '}';
  }
}
