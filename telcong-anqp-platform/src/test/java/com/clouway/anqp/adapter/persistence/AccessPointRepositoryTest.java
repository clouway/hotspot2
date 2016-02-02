package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.AccessPoint;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewAccessPoint;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 */
public class AccessPointRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private PersistentAccessPointRepository repository = new PersistentAccessPointRepository(datastore);

  @Test
  public void findById() throws Exception {
    NewAccessPoint ap = new NewAccessPoint("ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    Object id = repository.create(ap);

    AccessPoint got = repository.findById(id).get();
    AccessPoint want = new AccessPoint(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<AccessPoint> found = repository.findById("id");

    assertFalse(found.isPresent());
  }

  @Test
  public void findByIp() throws Exception {
    NewAccessPoint ap = new NewAccessPoint("ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    Object id = repository.create(ap);

    AccessPoint got = repository.findByIp("ip").get();
    AccessPoint want = new AccessPoint(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownIp() throws Exception {
    Optional<AccessPoint> found = repository.findByIp("ip");

    assertFalse(found.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewAccessPoint ap1 = new NewAccessPoint("ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1");
    NewAccessPoint ap2 = new NewAccessPoint("ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2");

    Object id1 = repository.create(ap1);
    Object id2 = repository.create(ap2);

    List<AccessPoint> got = repository.findAll();

    List<AccessPoint> want = Lists.newArrayList(
            new AccessPoint(id1, "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1"),
            new AccessPoint(id2, "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2")
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewAccessPoint("ip", new MacAddress("aa:bb"), "sn", "model"));

    AccessPoint ap = new AccessPoint(id, "ip2", new MacAddress("bb:aa"), "sn2", "model2");

    repository.update(ap);

    AccessPoint found = repository.findById(id).get();

    assertThat(found, deepEquals(ap));
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewAccessPoint("ip", new MacAddress("aa:bb"), "sn", "model"));

    repository.delete(id);

    List<AccessPoint> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewAccessPoint("ip", new MacAddress("aa:bb"), "sn", "model"));
    repository.delete("id");

    List<AccessPoint> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}