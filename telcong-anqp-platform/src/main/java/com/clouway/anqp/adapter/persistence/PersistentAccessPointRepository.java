package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.AccessPoint;
import com.clouway.anqp.AccessPointRepository;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewAccessPoint;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

/**
 */
class PersistentAccessPointRepository implements AccessPointRepository {
  private final Datastore datastore;

  @Inject
  public PersistentAccessPointRepository(Datastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public Object create(NewAccessPoint ap) {
    return datastore.save(adapt(ap));
  }

  @Override
  public void update(AccessPoint ap) {
    datastore.save(adapt(ap));
  }

  @Override
  public void delete(Object id) {
    datastore.deleteById(AccessPointEntity.class, id);
  }

  @Override
  public Optional<AccessPoint> findById(Object id) {
    AccessPointEntity entity = datastore.findOne(AccessPointEntity.class, Filter.where("_id").is(id));

    return adapt(entity);
  }

  @Override
  public Optional<AccessPoint> findByIp(String ip) {
    AccessPointEntity entity = datastore.findOne(AccessPointEntity.class, Filter.where("ip").is(ip));

    return adapt(entity);
  }

  @Override
  public List<AccessPoint> findAll() {
    List<AccessPointEntity> entities = datastore.findAll(AccessPointEntity.class);

    return adapt(entities);
  }

  private NewAccessPointEntity adapt(NewAccessPoint ap) {
    return new NewAccessPointEntity(ap.ip, ap.mac.value, ap.serialNumber, ap.model);
  }

  private AccessPointEntity adapt(AccessPoint ap) {
    return new AccessPointEntity(ap.id, ap.ip, ap.mac.value, ap.serialNumber, ap.model);
  }

  private Optional<AccessPoint> adapt(AccessPointEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new AccessPoint(entity.getId(), entity.getIp(), new MacAddress(entity.getMac()), entity.getSerialNumber(), entity.getModel()));
  }

  private List<AccessPoint> adapt(List<AccessPointEntity> entities) {
    List<AccessPoint> aps = Lists.newArrayList();

    for (AccessPointEntity entity : entities) {
      aps.add(new AccessPoint(entity.getId(), entity.getIp(), new MacAddress(entity.getMac()), entity.getSerialNumber(), entity.getModel()));
    }

    return aps;
  }
}