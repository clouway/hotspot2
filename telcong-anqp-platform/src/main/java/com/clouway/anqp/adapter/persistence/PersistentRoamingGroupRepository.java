package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.clouway.anqp.api.datastore.UpdateStatement;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

/**
 */
class PersistentRoamingGroupRepository implements RoamingGroupRepository {
  private final Datastore datastore;

  @Inject
  public PersistentRoamingGroupRepository(Datastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public Object create(NewRoamingGroup group) {
    return datastore.save(adapt(group));
  }

  @Override
  public void update(RoamingGroupRequest group) {
    datastore.save(adapt(group));
  }

  @Override
  public void assignOperators(ID roamingGroupID, List<ID> ids) {
    RoamingGroupEntity entity = datastore.findById(RoamingGroupEntity.class, roamingGroupID.value);

    if (entity == null) {
      throw new NotFoundException("Roaming group is unknown!");
    }

    List<Object> activeOperatorIDs = getActiveOperatorIDs(entity, ids);

    datastore.update(RoamingGroupEntity.class, Filter.where("_id").is(roamingGroupID.value), UpdateStatement.update("operatorIDs").toBe(activeOperatorIDs));
  }

  @Override
  public void removeOperators(ID roamingGroupID, List<ID> ids) {
    RoamingGroupEntity entity = datastore.findById(RoamingGroupEntity.class, roamingGroupID.value);

    if (entity == null) {
      throw new NotFoundException("Roaming group is unknown!");
    }

    List<Object> unnecessaryIDs = Lists.newArrayList();

    for (ID id : ids) {
      unnecessaryIDs.add(id.value);
    }

    entity.operatorIDs.removeAll(unnecessaryIDs);

    datastore.update(RoamingGroupEntity.class, Filter.where("_id").is(roamingGroupID.value), UpdateStatement.update("operatorIDs").toBe(entity.operatorIDs));
  }

  @Override
  public void delete(ID id) {
    datastore.deleteById(RoamingGroupEntity.class, id.value);
  }

  @Override
  public Optional<RoamingGroup> findById(ID id) {
    RoamingGroupEntity entity = datastore.findOne(RoamingGroupEntity.class, Filter.where("_id").is(id.value));

    if (entity == null) {
      return Optional.absent();
    }

    List<OperatorEntity> operatorEntities = datastore.findAllObjectsByFilter(OperatorEntity.class, Filter.where("_id").in(entity.operatorIDs));

    return Optional.of(adapt(entity, operatorEntities));
  }

  @Override
  public List<RoamingGroup> findAll() {
    List<RoamingGroup> groups = Lists.newArrayList();
    List<RoamingGroupEntity> entities = datastore.findAll(RoamingGroupEntity.class);

    for (RoamingGroupEntity groupEntity : entities) {
      List<OperatorEntity> operatorEntities = datastore.findAllObjectsByFilter(OperatorEntity.class, Filter.where("_id").in(groupEntity.operatorIDs));
      groups.add(adapt(groupEntity, operatorEntities));
    }

    return groups;
  }

  private List<Object> getActiveOperatorIDs(RoamingGroupEntity entity, List<ID> ids) {
    List<Object> operatorIDs = Lists.newArrayList();

    for (ID id : ids) {
      operatorIDs.add(id.value);
    }

    operatorIDs.addAll(entity.operatorIDs);

    // We can assign only active operators to roaming group.
    List<OperatorEntity> entities = datastore.findAllObjectsByFilter(OperatorEntity.class, Filter.where("_id").in(operatorIDs).and("state").is(OperatorState.ACTIVE.name()));

    return getIDOfOperators(entities);
  }

  private NewRoamingGroupEntity adapt(NewRoamingGroup group) {
    return new NewRoamingGroupEntity(group.name, group.description, group.type);
  }

  private RoamingGroup adapt(RoamingGroupEntity groupEntity, List<OperatorEntity> operatorEntities) {
    return new RoamingGroup(new ID(groupEntity._id), groupEntity.name, groupEntity.description, RoamingGroupType.valueOf(groupEntity.type), adaptToOperators(operatorEntities));
  }

  private List<Operator> adaptToOperators(List<OperatorEntity> entities) {
    List<Operator> operators = Lists.newArrayList();

    for (OperatorEntity entity : entities) {
      IpType ipType = IpType.valueOf(entity.ipType);

      operators.add(new Operator(new ID(entity._id), entity.name, OperatorState.valueOf(entity.state), entity.description, entity.domainName, entity.friendlyName, entity.emergencyNumber, ipType));
    }

    return operators;
  }

  private List<Object> getIDOfOperators(List<OperatorEntity> entities) {
    List<Object> ids = Lists.newArrayList();

    for (OperatorEntity entity : entities) {
      ids.add(entity._id);
    }

    return ids;
  }

  private RoamingGroupRequestEntity adapt(RoamingGroupRequest request) {
    return new RoamingGroupRequestEntity(request.id.value, request.name, request.description, request.type.name());
  }
}
