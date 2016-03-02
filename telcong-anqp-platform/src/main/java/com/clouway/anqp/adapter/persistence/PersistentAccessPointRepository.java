package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.IPv4.Availability;
import com.clouway.anqp.api.datastore.Datastore;
import com.clouway.anqp.api.datastore.Filter;
import com.clouway.anqp.core.NotFoundException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

import static com.clouway.anqp.VenueName.defaultName;

/**
 */
class PersistentAccessPointRepository implements AccessPointRepository {
  private final Datastore datastore;
  private final IPv4AvailabilityCatalog v4Catalog;

  @Inject
  public PersistentAccessPointRepository(Datastore datastore, IPv4AvailabilityCatalog v4Catalog) {
    this.datastore = datastore;
    this.v4Catalog = v4Catalog;
  }

  @Override
  public Object create(NewAccessPoint ap) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(ap.operatorId.value));

    if (count == 0) {
      throw new NotFoundException("AP creation failed due to non-existing operator");
    }

    return datastore.save(adapt(ap));
  }

  @Override
  public void update(AccessPointRequest request) {
    datastore.save(adapt(request));
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

  @Override
  public QueryList findQueryList(Object id) {
    AccessPointEntity apEntity = datastore.findById(AccessPointEntity.class, id);

    if (apEntity == null) {
      throw new NotFoundException("Unsuccessful retrieving of query list due to missing AP");
    }

    OperatorEntity operatorEntity = datastore.findById(OperatorEntity.class, apEntity.operatorId);

    if (operatorEntity == null) {
      throw new NotFoundException("Unsuccessful retrieving of query list due to missing operator");
    }

    Availability availability = v4Catalog.findAvailability(operatorEntity.ipV4).get();

    return new QueryList(availability.id);
  }

  @Override
  public CapabilityList findCapabilityList(ID id) {
    AccessPointEntity entity = datastore.findById(AccessPointEntity.class, id.value);

    if (entity == null) {
      throw new NotFoundException("Not found AP with id " + id.value);
    }

    return adaptCapabilityEntities(entity.capabilities);
  }

  private AccessPointRequestEntity adapt(AccessPointRequest ap) {
    return new AccessPointRequestEntity(ap.id.value, ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue), adapt(ap.geoLocation), adapt(ap.civicLocation), adaptCapabilities(ap.capabilities));
  }

  private List<AccessPoint> adapt(List<AccessPointEntity> entities) {
    List<AccessPoint> aps = Lists.newArrayList();

    for (AccessPointEntity entity : entities) {
      aps.add(new AccessPoint(new ID(entity._id), entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue), adapt(entity.geoLocation), adapt(entity.civicLocation), adaptCapabilityEntities(entity.capabilities)));
    }

    return aps;
  }

  private Optional<AccessPoint> adapt(AccessPointEntity entity) {
    if (entity == null) {
      return Optional.absent();
    }

    return Optional.of(new AccessPoint(new ID(entity._id), entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue), adapt(entity.geoLocation), adapt(entity.civicLocation), adaptCapabilityEntities(entity.capabilities)));
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

  private NewAccessPointEntity adapt(NewAccessPoint ap) {
    return new NewAccessPointEntity(ap.operatorId.value, ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue), adapt(ap.geoLocation), adapt(ap.civicLocation), adaptCapabilities(ap.capabilities));
  }

  private VenueEntity adapt(Venue venue) {
    List<VenueNameEntity> nameEntities = Lists.newArrayList();

    for (VenueName info : venue.names) {
      nameEntities.add(new VenueNameEntity(info.name, info.language.code));
    }

    return new VenueEntity(venue.group.name, venue.type.name, nameEntities);
  }

  private GeoLocationEntity adapt(GeoLocation location) {
    return new GeoLocationEntity(location.latitude, location.longitude);
  }

  private GeoLocation adapt(GeoLocationEntity location) {
    return new GeoLocation(location.latitude, location.longitude);
  }

  private CivicLocationEntity adapt(CivicLocation civicLocation) {
    return new CivicLocationEntity(civicLocation.country, civicLocation.city, civicLocation.street, civicLocation.streetNumber, civicLocation.postCode);
  }

  private CivicLocation adapt(CivicLocationEntity address) {
    return new CivicLocation(address.country, address.city, address.street, address.streetNumber, address.postCode);
  }

  private List<CapabilityEntity> adaptCapabilities(CapabilityList capabilities) {
    List<CapabilityEntity> entities = Lists.newArrayList();

    for (Capability capability : capabilities.values) {
      entities.add(new CapabilityEntity(capability.id, capability.name));
    }

    return entities;
  }

  private CapabilityList adaptCapabilityEntities(List<CapabilityEntity> entities) {
    List<Capability> capabilities = Lists.newArrayList();

    for (CapabilityEntity entity : entities) {
      capabilities.add(new Capability(entity.id, entity.name));
    }

    return new CapabilityList(capabilities);
  }
}