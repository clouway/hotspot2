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

import static com.clouway.anqp.VenueName.defaultName;

/**
 */
class PersistentOperatorRepository implements OperatorRepository {
  private final Datastore datastore;

  @Inject
  public PersistentOperatorRepository(Datastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public Object create(NewOperator operator) {
    Long count = datastore.entityCount(NewOperatorEntity.class, Filter.where("name").is(operator.name));

    if (count != 0) {
      throw new OperatorException("Operator with name: " + operator.name + " already exists.");
    }

    return datastore.save(adapt(operator));
  }

  @Override
  public List<Operator> findAll() {
    List<OperatorEntity> entities = datastore.findAll(OperatorEntity.class);

    return adapt(entities);
  }

  @Override
  public Optional<Operator> findById(ID id) {
    OperatorEntity entity = datastore.findOne(OperatorEntity.class, Filter.where("_id").is(id.value));

    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(adapt(entity));
  }

  @Override
  public void activate(ID id) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(id.value));

    if (count == 0) {
      throw new NotFoundException("Operator is undefined!");
    }

    datastore.update(OperatorEntity.class, Filter.where("_id").is(id.value), UpdateStatement.update("state").toBe(OperatorState.ACTIVE.name()));
  }

  @Override
  public void deactivate(ID id) {
    Long groupCount = datastore.entityCount(RoamingGroupEntity.class, Filter.where("operatorIDs").is(id.value));

    if (groupCount > 0) {
      throw new OperatorException("Operator is assigned to roaming group.");
    }

    Long operCount = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(id.value));

    if (operCount == 0) {
      throw new NotFoundException("Operator is undefined!");
    }

    datastore.update(OperatorEntity.class, Filter.where("_id").is(id.value), UpdateStatement.update("state").toBe(OperatorState.INACTIVE.name()));
  }

  @Override
  public void assignAccessPoints(ID operID, List<ID> apIDs) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(operID.value).and("state").is(OperatorState.ACTIVE.name()));

    if (count == 0) {
      throw new NotFoundException("Operator is unknown or deactivate!");
    }

    List<Object> ids = Lists.newArrayList();

    for (ID id : apIDs) {
      ids.add(id.value);
    }

    datastore.update(AccessPointEntity.class, Filter.where("_id").in(ids), UpdateStatement.updateBulk("operatorId").toBe(operID.value));
  }

  @Override
  public List<AccessPoint> findAccessPoints(ID id) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(id.value));

    if (count == 0) {
      throw new NotFoundException("Operator is unknown!");
    }

    List<AccessPointEntity> entities = datastore.findAllObjectsByFilter(AccessPointEntity.class, Filter.where("operatorId").is(id.value));

    return adaptToAPs(entities);
  }

  @Override
  public void update(Operator operator) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("name").is(operator.name).and("_id").isNot(operator.id.value));

    if (count != 0) {
      throw new OperatorException("Operator with name: " + operator.name + " already exists.");
    }

    datastore.save(adapt(operator));
  }

  @Override
  public void updateEmergencyNumber(NewEmergencyNumber newNumber) {
    long count = datastore.entityCount(OperatorEntity.class, Filter.where("emergencyNumber").is(newNumber.value));

    if (count != 0) {
      throw new EmergencyNumberException("Emergency call number: " + newNumber.value + " is already used from another operator.");
    }

    datastore.update(OperatorEntity.class, Filter.where("_id").is(newNumber.operatorID.value), UpdateStatement.update("emergencyNumber").toBe(newNumber.value));
  }

  @Override
  public void delete(ID id) {
    datastore.deleteById(OperatorEntity.class, id.value);
  }

  private OperatorEntity adapt(Operator operator) {
    return new OperatorEntity(operator.id.value, operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber, operator.ipType.name());
  }

  private NewOperatorEntity adapt(NewOperator operator) {
    return  new NewOperatorEntity(operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber, operator.ipType.name());
  }

  private List<Operator> adapt(List<OperatorEntity> entities) {
    List<Operator> operators = Lists.newArrayList();

    for (OperatorEntity entity : entities) {
      operators.add(adapt(entity));
    }

    return operators;
  }

  private Operator adapt(OperatorEntity entity) {
    IpType ipType = IpType.valueOf(entity.ipType);

    return new Operator(new ID(entity._id), entity.name, OperatorState.valueOf(entity.state), entity.description, entity.domainName, entity.friendlyName,entity.emergencyNumber, ipType);
  }

  private List<AccessPoint> adaptToAPs(List<AccessPointEntity> entities) {
    List<AccessPoint> aps = Lists.newArrayList();

    for (AccessPointEntity entity : entities) {
      aps.add(new AccessPoint(new ID(entity._id), entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue)));
    }

    return aps;
  }

  private Venue adapt(VenueEntity entity) {
    if (entity.venueNames.isEmpty()) {
      return new Venue(new VenueGroup(entity.group), new VenueType(entity.type), Lists.newArrayList(defaultName()));
    }
    List<VenueName> names = Lists.newArrayList();

    for (VenueNameEntity infoEntity : entity.venueNames) {
      names.add(new VenueName(infoEntity.name, new Language(infoEntity.language)));
    }

    return new Venue(new VenueGroup(entity.group), new VenueType(entity.type), names);
  }
}
