package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.Auth.Info;
import com.clouway.anqp.Auth.Type;
import com.clouway.anqp.EAP.Method;
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
    Long operCount = datastore.entityCount(OperatorEntity.class, Filter.where("name").is(operator.name));

    if (operCount != 0) {
      throw new OperatorException("Operator with name: " + operator.name + " already exists.");
    }

    Long countEmergency = datastore.entityCount(OperatorEntity.class, Filter.where("emergencyNumber").is(operator.emergencyNumber));

    if (countEmergency != 0) {
      throw new EmergencyNumberException("Emergency call number: " + operator.emergencyNumber + " is already used from another operator.");
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
    Long operCount = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(id.value));

    if (operCount == 0) {
      throw new NotFoundException("Operator is undefined!");
    }

    Long groupCount = datastore.entityCount(RoamingGroupEntity.class, Filter.where("operatorIDs").is(id.value));

    if (groupCount > 0) {
      throw new OperatorException("Operator is assigned to roaming group.");
    }

    Long apCount = datastore.entityCount(AccessPointEntity.class, Filter.where("operatorId").is(id.value));

    if (apCount > 0) {
      throw new OperatorException("Operator has assigned access points.");
    }

    datastore.update(OperatorEntity.class, Filter.where("_id").is(id.value), UpdateStatement.update("state").toBe(OperatorState.INACTIVE.name()));
  }

  @Override
  public void assignAccessPoints(ID operID, List<ID> apIDs) {
    Long count = datastore.entityCount(OperatorEntity.class, Filter.where("_id").is(operID.value).and("state").is(OperatorState.ACTIVE.name()));

    if (count == 0) {
      throw new NotFoundException("Operator is unknown or deactivate!");
    }

    List<Object> ids = getIDValues(apIDs);

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
  public void assignServiceProviders(ID operID, List<ID> spIDs) {
    OperatorEntity entity = datastore.findById(OperatorEntity.class, operID.value);

    if (entity == null) {
      throw new NotFoundException("Operator in unknown");
    }

    List<Object> ids = getIDValues(spIDs);
    entity.serviceProviderIDs.addAll(ids);

    datastore.update(OperatorEntity.class, Filter.where("_id").is(operID.value), UpdateStatement.update("serviceProviderIDs").toBe(entity.serviceProviderIDs));
  }

  @Override
  public List<ServiceProvider> findServiceProviders(ID id) {
    OperatorEntity entity = datastore.findById(OperatorEntity.class, id.value);

    if (entity == null) {
      throw new NotFoundException("Operator is unknown");
    }

    List<ServiceProviderEntity> entities = datastore.findAllObjectsByFilter(ServiceProviderEntity.class, Filter.where("_id").in(entity.serviceProviderIDs));

    return adaptToServiceProviders(entities);
  }

  @Override
  public void removeServiceProviders(ID operID, List<ID> spIDs) {
    OperatorEntity entity = datastore.findById(OperatorEntity.class, operID.value);

    if (entity == null) {
      throw new NotFoundException("Operator is unknown");
    }

    List<Object> ids = getIDValues(spIDs);
    entity.serviceProviderIDs.removeAll(ids);

    datastore.update(OperatorEntity.class, Filter.where("_id").is(operID.value), UpdateStatement.update("serviceProviderIDs").toBe(entity.serviceProviderIDs));
  }

  @Override
  public void update(Operator operator) {
    Long operCount = datastore.entityCount(OperatorEntity.class, Filter.where("name").is(operator.name).and("_id").isNot(operator.id.value));

    if (operCount != 0) {
      throw new OperatorException("Operator with name: " + operator.name + " already exists.");
    }

    Long countEmergency = datastore.entityCount(OperatorEntity.class, Filter.where("emergencyNumber").is(operator.emergencyNumber).and("_id").isNot(operator.id.value));

    if (countEmergency != 0) {
      throw new EmergencyNumberException("Emergency call number: " + operator.emergencyNumber + " is already used from another operator.");
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
    Long groupCount = datastore.entityCount(RoamingGroupEntity.class, Filter.where("operatorIDs").is(id.value));

    if (groupCount > 0) {
      throw new OperatorException("Operator is assigned to roaming group.");
    }

    Long apCount = datastore.entityCount(AccessPointEntity.class, Filter.where("operatorId").is(id.value));

    if (apCount > 0) {
      throw new OperatorException("Operator has assigned access points.");
    }

    datastore.deleteById(OperatorEntity.class, id.value);
  }

  private List<Operator> adapt(List<OperatorEntity> entities) {
    List<Operator> operators = Lists.newArrayList();

    for (OperatorEntity entity : entities) {
      operators.add(adapt(entity));
    }

    return operators;
  }

  private Operator adapt(OperatorEntity entity) {

    return new Operator(new ID(entity._id), entity.name, OperatorState.valueOf(entity.state), entity.description, entity.domainName, entity.friendlyName, entity.emergencyNumber, new IPv4(IPv4.Availability.valueOf(entity.ipV4)), new IPv6(IPv6.Availability.valueOf(entity.ipV6)));
  }

  private List<AccessPoint> adaptToAPs(List<AccessPointEntity> entities) {
    List<AccessPoint> aps = Lists.newArrayList();

    for (AccessPointEntity entity : entities) {
      aps.add(new AccessPoint(new ID(entity._id), entity.ip, new MacAddress(entity.mac), entity.serialNumber, entity.model, adapt(entity.venue), adapt(entity.geoLocation), adapt(entity.civicLocation), adaptCapabilityEntities(entity.capabilities)));
    }

    return aps;
  }

  private CivicLocation adapt(CivicLocationEntity address) {
    return new CivicLocation(address.country, address.city, address.street, address.streetNumber, address.postCode);
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

  private GeoLocation adapt(GeoLocationEntity location) {
    return new GeoLocation(location.latitude, location.longitude);
  }

  private List<ServiceProvider> adaptToServiceProviders(List<ServiceProviderEntity> entities) {
    List<ServiceProvider> sps = Lists.newArrayList();

    for (ServiceProviderEntity entity : entities) {
      DomainNameList list = new DomainNameList(entity.domainNames);

      sps.add(new ServiceProvider(new ID(entity._id), entity.name, entity.description, adaptToNetwork3GPPs(entity.networks), list, adaptToConsortiums(entity.consortiums), adaptToNAIs(entity.naiRealms)));
    }

    return sps;
  }

  private List<NAI> adaptToNAIs(List<NaiEntity> entities) {
    List<NAI> nais = Lists.newArrayList();

    for (NaiEntity entity : entities) {
      nais.add(new NAI(entity.name, Encoding.valueOf(entity.encoding), adaptToEAPs(entity.eaps)));
    }

    return nais;
  }

  private List<EAP> adaptToEAPs(List<EapEntity> entities) {
    List<EAP> eaps = Lists.newArrayList();

    for (EapEntity entity : entities) {
      eaps.add(new EAP(Method.valueOf(entity.method), adaptToAuths(entity.auths)));
    }

    return eaps;
  }

  private List<Auth> adaptToAuths(List<AuthEntity> entities) {
    List<Auth> auths = Lists.newArrayList();

    for (AuthEntity entity : entities) {
      auths.add(new Auth(Info.valueOf(entity.info), Type.valueOf(entity.type)));
    }

    return auths;
  }

  private List<RoamingConsortium> adaptToConsortiums(List<RoamingConsortiumEntity> entities) {
    List<RoamingConsortium> consortiums = Lists.newArrayList();

    for (RoamingConsortiumEntity entity : entities) {
      consortiums.add(new RoamingConsortium(entity.name, entity.organizationID));
    }

    return consortiums;
  }

  private List<Network3GPP> adaptToNetwork3GPPs(List<Network3GPPEntity> entities) {
    List<Network3GPP> networks = Lists.newArrayList();

    for (Network3GPPEntity entity : entities) {
      networks.add(new Network3GPP(entity.name, entity.mobileCountryCode, entity.mobileNetworkCode));
    }

    return networks;
  }

  private OperatorRequestEntity adapt(Operator operator) {
    return new OperatorRequestEntity(operator.id.value, operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber,operator.ipV4.availability.name(), operator.ipV6.availability.name());
  }

  private NewOperatorEntity adapt(NewOperator operator) {
    return new NewOperatorEntity(operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber, operator.ipV4.availability.name(), operator.ipV6.availability.name());
  }

  private CapabilityList adaptCapabilityEntities(List<CapabilityEntity> entities) {
    List<Capability> list = Lists.newArrayList();

    for (CapabilityEntity entity : entities) {
      list.add(new Capability(entity.id, entity.name));
    }

    return new CapabilityList(list);
  }

  private List<Object> getIDValues(List<ID> ids) {
    List<Object> values = Lists.newArrayList();

    for (ID id : ids) {
      values.add(id.value);
    }

    return values;
  }
}
