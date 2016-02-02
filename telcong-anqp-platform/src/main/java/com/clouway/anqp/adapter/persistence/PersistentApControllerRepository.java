package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ApController;
import com.clouway.anqp.ApControllerRepository;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewApController;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

/**
 */
class PersistentApControllerRepository implements ApControllerRepository {
  private final Datastore datastore;

  @Inject
  public PersistentApControllerRepository(Datastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public Object create(NewApController controller) {
    return datastore.save(adapt(controller));
  }

  @Override
  public void update(ApController controller) {
    datastore.save(adapt(controller));
  }

  @Override
  public void delete(Object id) {
    datastore.deleteById(ApControllerEntity.class, id);
  }

  @Override
  public Optional<ApController> findById(Object id) {
    ApControllerEntity entity = datastore.findOne(ApControllerEntity.class, Filter.where("_id").is(id));

    return adapt(entity);
  }

  @Override
  public Optional<ApController> findByIp(String ip) {
    ApControllerEntity entity = datastore.findOne(ApControllerEntity.class, Filter.where("ip").is(ip));

    return adapt(entity);
  }

  @Override
  public List<ApController> findAll() {
    List<ApControllerEntity> entities = datastore.findAll(ApControllerEntity.class);

    return adapt(entities);
  }

  private NewApControllerEntity adapt(NewApController controller) {
    return new NewApControllerEntity(controller.ip, controller.mac.value, controller.serialNumber, controller.model);
  }

  private ApControllerEntity adapt(ApController controller) {
    return new ApControllerEntity(controller.id, controller.ip, controller.mac.value, controller.serialNumber, controller.model);
  }

  private Optional<ApController> adapt(ApControllerEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new ApController(entity.getId(), entity.getIp(), new MacAddress(entity.getMac()), entity.getSerialNumber(), entity.getModel()));
  }

  private List<ApController> adapt(List<ApControllerEntity> entities) {
    List<ApController> controllers = Lists.newArrayList();

    for (ApControllerEntity entity : entities) {
      controllers.add(new ApController(entity.getId(), entity.getIp(), new MacAddress(entity.getMac()), entity.getSerialNumber(), entity.getModel()));
    }

    return controllers;
  }
}