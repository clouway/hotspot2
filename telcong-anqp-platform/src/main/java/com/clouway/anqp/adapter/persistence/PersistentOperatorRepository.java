package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.EmergencyNumberException;
import com.clouway.anqp.NewEmergencyNumber;
import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorException;
import com.clouway.anqp.OperatorRepository;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.clouway.anqp.api.datastore.UpdateStatement;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

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
  public Optional<Operator> findById(Object id) {
    OperatorEntity entity = datastore.findOne(OperatorEntity.class, Filter.where("_id").is(id));

    return adapt(entity);
  }

  @Override
  public void update(Operator operator) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("name").is(operator.name).and("_id").isNot(operator.id));

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

    datastore.update(OperatorEntity.class, Filter.where("_id").is(newNumber.operatorID), UpdateStatement.update("emergencyNumber").toBe(newNumber.value));
  }

  @Override
  public void delete(Object id) {
    datastore.deleteById(OperatorEntity.class, id);
  }

  private List<Operator> adapt(List<OperatorEntity> entities) {
    List<Operator> operators = Lists.newArrayList();

    for(OperatorEntity entity : entities) {
      operators.add(new Operator(entity._id, entity.name, entity.description, entity.domainName, entity.friendlyName,entity.emergencyNumber));
    }

    return operators;
  }

  private Optional<Operator> adapt(OperatorEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new Operator(entity._id, entity.name, entity.description, entity.domainName, entity.friendlyName,entity.emergencyNumber));
  }

  private OperatorEntity adapt(Operator operator) {
    return new OperatorEntity(operator.id, operator.name, operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber);
  }

  private NewOperatorEntity adapt(NewOperator operator) {
    return  new NewOperatorEntity(operator.name, operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber);
  }
}
