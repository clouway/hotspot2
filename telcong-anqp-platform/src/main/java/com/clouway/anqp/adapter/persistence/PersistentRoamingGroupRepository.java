package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.NewRoamingGroup;
import com.clouway.anqp.RoamingGroup;
import com.clouway.anqp.RoamingGroupRepository;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
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
  public void update(RoamingGroup group) {
    datastore.save(adapt(group));
  }

  @Override
  public void delete(Object id) {
    datastore.deleteById(RoamingGroupEntity.class, id);
  }

  @Override
  public Optional<RoamingGroup> findById(Object id) {
    RoamingGroupEntity entity = datastore.findOne(RoamingGroupEntity.class, Filter.where("_id").is(id));

    return adapt(entity);
  }

  @Override
  public List<RoamingGroup> findAll() {
    List<RoamingGroupEntity> entities = datastore.findAll(RoamingGroupEntity.class);

    return adapt(entities);
  }

  private NewRoamingGroupEntity adapt(NewRoamingGroup group) {
    return new NewRoamingGroupEntity(group.name, group.description, group.type);
  }

  private RoamingGroupEntity adapt(RoamingGroup group) {
    return new RoamingGroupEntity(group.id, group.name, group.description, group.type);
  }

  private Optional<RoamingGroup> adapt(RoamingGroupEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new RoamingGroup(entity.getId(), entity.getName(), entity.getDescription(), entity.getType()));
  }

  private List<RoamingGroup> adapt(List<RoamingGroupEntity> entities) {
    List<RoamingGroup> groups = Lists.newArrayList();

    for (RoamingGroupEntity entity : entities) {
      groups.add(new RoamingGroup(entity.getId(), entity.getName(), entity.getDescription(), entity.getType()));
    }

    return groups;
  }
}
