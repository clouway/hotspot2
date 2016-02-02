package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ApController;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewApController;
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
public class ApControllerRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private PersistentApControllerRepository repository = new PersistentApControllerRepository(datastore);

  @Test
  public void findById() throws Exception {
    NewApController controller = new NewApController("ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    Object id = repository.create(controller);

    ApController got = repository.findById(id).get();
    ApController want = new ApController(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<ApController> found = repository.findById("id");

    assertFalse(found.isPresent());
  }

  @Test
  public void findByIp() throws Exception {
    NewApController controller = new NewApController("ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    Object id = repository.create(controller);

    ApController got = repository.findByIp("ip").get();
    ApController want = new ApController(id, "ip", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn", "model");

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownIp() throws Exception {
    Optional<ApController> found = repository.findByIp("ip");

    assertFalse(found.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewApController controller1 = new NewApController("ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1");
    NewApController controller2 = new NewApController("ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2");

    Object id1 = repository.create(controller1);
    Object id2 = repository.create(controller2);

    List<ApController> got = repository.findAll();

    List<ApController> want = Lists.newArrayList(
            new ApController(id1, "ip1", new MacAddress("aa:bb:cc:dd:ee:ff"), "sn1", "model1"),
            new ApController(id2, "ip2", new MacAddress("ff:ee:dd:cc:bb:aa"), "sn2", "model2")
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewApController("ip", new MacAddress("aa:bb"), "sn", "model"));

    ApController controller = new ApController(id, "ip2", new MacAddress("bb:aa"), "sn2", "model2");

    repository.update(controller);

    ApController found = repository.findById(id).get();

    assertThat(found, deepEquals(controller));
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewApController("ip", new MacAddress("aa:bb"), "sn", "model"));

    repository.delete(id);

    List<ApController> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewApController("ip", new MacAddress("aa:bb"), "sn", "model"));
    repository.delete("id");

    List<ApController> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}