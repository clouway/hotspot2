package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.clouway.anqp.core.NotFoundException;
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
public class RoamingGroupRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());

  private OperatorRepository operatorRepository = new PersistentOperatorRepository(datastore);
  private RoamingGroupRepository roamingRepository = new PersistentRoamingGroupRepository(datastore);

  @Test
  public void findByUnknownId() throws Exception {
    Optional<RoamingGroup> found = roamingRepository.findById(new ID("id"));

    assertFalse(found.isPresent());
  }

  @Test
  public void findRoamingGroupWithoutOperators() throws Exception {
    Object id = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.PERMANENT));

    RoamingGroup got = roamingRepository.findById(new ID(id)).get();
    RoamingGroup want = new RoamingGroup(new ID(id), "name", "description", RoamingGroupType.PERMANENT, Lists.<Operator>newArrayList());

    assertThat(got, deepEquals(want));
  }

  @Test
  public void findRoamingGroupsWithoutOperators() throws Exception {
    Object id1 = roamingRepository.create(new NewRoamingGroup("name1", "description1", RoamingGroupType.REGIONAL));
    Object id2 = roamingRepository.create(new NewRoamingGroup("name2", "description2", RoamingGroupType.INTERNATIONAL));

    List<RoamingGroup> got = roamingRepository.findAll();

    List<RoamingGroup> want = Lists.newArrayList(
            new RoamingGroup(new ID(id1), "name1", "description1", RoamingGroupType.REGIONAL, Lists.<Operator>newArrayList()),
            new RoamingGroup(new ID(id2), "name2", "description2", RoamingGroupType.INTERNATIONAL, Lists.<Operator>newArrayList())
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void update() throws Exception {
    Object operID = operatorRepository.create(new NewOperator("name", OperatorState.ACTIVE, "description", "dName", "fName", "emergency"));
    Object groupID = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));

    roamingRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID)));

    RoamingGroupRequest newRoamingGroup = new RoamingGroupRequest(new ID(groupID), "newName", "newDescription", RoamingGroupType.INTERNATIONAL);

    roamingRepository.update(newRoamingGroup);

    RoamingGroup got = roamingRepository.findById(new ID(groupID)).get();
    RoamingGroup want = new RoamingGroup(
            new ID(groupID), "newName", "newDescription", RoamingGroupType.INTERNATIONAL,
            Lists.newArrayList(new Operator(new ID(operID), "name", OperatorState.ACTIVE, "description", "dName", "fName", "emergency"))
    );

    assertThat(got, deepEquals(want));
  }

  @Test
  public void assignOperators() throws Exception {
    Object operID1 = operatorRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"));
    Object operID2 = operatorRepository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "emergency"));

    Object groupID = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.INTERNATIONAL));

    roamingRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID1)));
    roamingRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID2)));

    RoamingGroup got = roamingRepository.findById(new ID(groupID)).get();

    List<Operator> operators = Lists.newArrayList(
            new Operator(new ID(operID1), "name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"),
            new Operator(new ID(operID2), "name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "emergency")
    );

    RoamingGroup want = new RoamingGroup(new ID(groupID), "name", "description", RoamingGroupType.INTERNATIONAL, operators);

    assertThat(got, deepEquals(want));
  }

  @Test
  public void assignDisabledOperator() throws Exception {
    Object operID1 =  operatorRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"));
    Object operID2 = operatorRepository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "emergency"));

    Object groupID = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.INTERNATIONAL));

    roamingRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID1), new ID(operID2)));

    RoamingGroup got = roamingRepository.findById(new ID(groupID)).get();

    List<Operator> operators = Lists.newArrayList(new Operator(new ID(operID1), "name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"));
    RoamingGroup want = new RoamingGroup(new ID(groupID), "name", "description", RoamingGroupType.INTERNATIONAL, operators);

    assertThat(got, deepEquals(want));
  }

  @Test(expected = NotFoundException.class)
  public void assignOperatorsToUnknownRoamingGroup() throws Exception {
    Object operID1 =  operatorRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"));
    Object operID2 = operatorRepository.create(new NewOperator("name2", OperatorState.INACTIVE, "description2", "dName2", "fName2", "emergency"));

    roamingRepository.assignOperators(new ID("groupID"), Lists.newArrayList(new ID(operID1), new ID(operID2)));
  }

  @Test
  public void removeOperatorsFromRoamingGroup() throws Exception {
    Object operID1 = operatorRepository.create(new NewOperator("name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency"));
    Object operID2 = operatorRepository.create(new NewOperator("name2", OperatorState.ACTIVE, "description2", "dName2", "fName2", "emergency"));

    Object groupID = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.INTERNATIONAL));

    roamingRepository.assignOperators(new ID(groupID), Lists.newArrayList(new ID(operID1), new ID(operID2)));

    roamingRepository.removeOperators(new ID(groupID), Lists.newArrayList(new ID(operID2)));

    List<RoamingGroup> got = roamingRepository.findAll();

    Operator operator = new Operator(new ID(operID1), "name1", OperatorState.ACTIVE, "description1", "dName1", "fName1", "emergency");
    List<RoamingGroup> want = Lists.newArrayList(new RoamingGroup(new ID(groupID), "name", "description", RoamingGroupType.INTERNATIONAL, Lists.newArrayList(operator)));

    assertThat(got, deepEquals(want));
  }

  @Test(expected = NotFoundException.class)
  public void removeOperatorsFromUnknownRoamingGroup() throws Exception {
    roamingRepository.removeOperators(new ID("groupID"), Lists.newArrayList(new ID("operatorID")));
  }

  @Test
  public void deleteById() throws Exception {
    Object id = roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));

    roamingRepository.delete(new ID(id));

    List<RoamingGroup> found = roamingRepository.findAll();

    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    roamingRepository.create(new NewRoamingGroup("name", "description", RoamingGroupType.REGIONAL));
    roamingRepository.delete(new ID("id"));

    List<RoamingGroup> found = roamingRepository.findAll();

    assertThat(found.size(), is(1));
  }
}
