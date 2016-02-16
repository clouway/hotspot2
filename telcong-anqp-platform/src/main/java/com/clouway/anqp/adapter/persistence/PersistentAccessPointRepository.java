package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

import static com.clouway.anqp.VenueName.defaultName;

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
    return new NewAccessPointEntity(ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue));
  }

  private AccessPointEntity adapt(AccessPoint ap) {
    return new AccessPointEntity(ap.id, ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue));
  }

  private Optional<AccessPoint> adapt(AccessPointEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new AccessPoint(entity._id, entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue)));
  }

  private List<AccessPoint> adapt(List<AccessPointEntity> entities) {
    List<AccessPoint> aps = Lists.newArrayList();

    for (AccessPointEntity entity : entities) {
      aps.add(new AccessPoint(entity._id, entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue)));
    }

    return aps;
  }

  private VenueEntity adapt(Venue venue) {
    List<VenueNameEntity> nameEntities = Lists.newArrayList();

    for (VenueName info : venue.names) {
      nameEntities.add(new VenueNameEntity(info.name, info.language.code));
    }

    return new VenueEntity(venue.group.name, venue.type.name, nameEntities);
  }

  private Venue adapt(VenueEntity entity) {
    if(entity.venueNames.isEmpty()){
      return new Venue(new VenueGroup(entity.group), new VenueType(entity.type), Lists.newArrayList(defaultName()));
    }
    List<VenueName> names = Lists.newArrayList();

    for (VenueNameEntity infoEntity : entity.venueNames) {
      names.add(new VenueName(infoEntity.name, new Language(infoEntity.language)));
    }

    return new Venue(new VenueGroup(entity.group), new VenueType(entity.type), names);
  }
}