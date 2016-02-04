package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.NewRoamingGroup;
import com.clouway.anqp.RoamingGroup;
import com.clouway.anqp.RoamingGroupRepository;
import com.clouway.anqp.RoamingGroupType;
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
 * @author Emil Georgiev <emil.georgiev@clouway.com>
 */
public class RoamingGroupRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());
  private RoamingGroupRepository repository = new PersistentRoamingGroupRepository(datastore);

  @Test
  public void findById() throws Exception {
    NewRoamingGroup group = new NewRoamingGroup("name", "description", RoamingGroupType.PERMANENT);

    Object id = repository.create(group);

    RoamingGroup got = repository.findById(id).get();
    RoamingGroup want = new RoamingGroup(id, "name", "description", RoamingGroupType.PERMANENT);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<RoamingGroup> found = repository.findById("id");

    assertFalse(found.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewRoamingGroup someGroup = new NewRoamingGroup("name1", "description1", RoamingGroupType.REGIONAL);
    NewRoamingGroup anotherGroup = new NewRoamingGroup("name2", "description2", RoamingGroupType.INTERNATIONAL);

    Object id1 = repository.create(someGroup);
    Object id2 = repository.create(anotherGroup);

    List<RoamingGroup> got = repository.findAll();

    List<RoamingGroup> want = Lists.newArrayList(
            new RoamingGroup(id1, "name1", "description1", RoamingGroupType.REGIONAL),
            new RoamingGroup(id2, "name2", "description2", RoamingGroupType.INTERNATIONAL)
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));

    RoamingGroup group = new RoamingGroup(id, "name", "description", RoamingGroupType.REGIONAL);

    repository.update(group);

    RoamingGroup found = repository.findById(id).get();

    assertThat(found, deepEquals(group));
  }

  @Test
  public void deleteById() throws Exception {
    Object id = repository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));

    repository.delete(id);

    List<RoamingGroup> found = repository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));
    repository.delete("id");

    List<RoamingGroup> found = repository.findAll();

    assertThat(found.size(), is(1));
  }
}
