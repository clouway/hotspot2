package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.clouway.anqp.Capability;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Delete;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.http.Put;

import java.util.List;

/**
 */
@Service
@At("/r/operators")
public class OperatorEndpoint {
  private final OperatorRepository repository;

  @Inject
  public OperatorEndpoint(OperatorRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewOperatorDTO dto = request.read(NewOperatorDTO.class).as(Json.class);
    NewOperator operator = adapt(dto);

    Object id = repository.create(operator);

    IdDTO idDTO = new IdDTO(id);

    return Reply.with(idDTO).as(Json.class).ok();
  }

  @Get
  public Reply<?> findAll() {
    List<Operator> operators = repository.findAll();

    List<OperatorDTO> dtos = adapt(operators);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id") String id) {
    Optional<Operator> operator = repository.findById(new ID(id));

    if (!operator.isPresent()) {
      return Reply.saying().notFound();
    }

    OperatorDTO dto = adapt(operator.get());

    return Reply.with(dto).as(Json.class).ok();
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    OperatorDTO dto = request.read(OperatorDTO.class).as(Json.class);
    Operator operator = adapt(id, dto);

    repository.update(operator);

    return Reply.saying().ok();
  }

  @Post
  @At("/:id/aps")
  public Reply<?> assignAccessPoints(@Named("id") String id, Request request) {
    List dtos = request.read(List.class).as(Json.class);
    List<ID> apIDs = adaptToIDs(dtos);

    repository.assignAccessPoints(new ID(id), apIDs);

    return Reply.saying().ok();
  }

  @Get
  @At("/:id/aps")
  public Reply<?> findAccessPoints(@Named("id") Object id) {
    List<AccessPoint> aps = repository.findAccessPoints(new ID(id));
    List<AccessPointDTO> dtos = adaptToAPs(aps);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Post
  @At("/:id/sps")
  public Reply<?> assignServiceProviders(@Named("id") Object id, Request request) {
    List dtos = request.read(List.class).as(Json.class);
    List<ID> spIDs = adaptToIDs(dtos);

    repository.assignServiceProviders(new ID(id), spIDs);

    return Reply.saying().ok();
  }

  @Put
  @At("/:id/sps/remove")
  public Reply<?> removeServiceProviders(@Named("id") String operID, Request request) {
    List dtos = request.read(List.class).as(Json.class);
    List<ID> spIDs = adaptToIDs(dtos);

    repository.removeServiceProviders(new ID(operID), spIDs);

    return Reply.saying().ok();
  }

  @Get
  @At("/:id/sps")
  public Reply<?> findServiceProviders(@Named("id") Object id) {
    List<ServiceProvider> sps = repository.findServiceProviders(new ID(id));
    List<ServiceProviderDTO> dtos = adaptToServiceProviderDTOs(sps);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Post
  @At("/:id/activate")
  public Reply<?> activate(@Named("id") Object id) {
    repository.activate(new ID(id));

    return Reply.saying().ok();
  }

  @Post
  @At("/:id/deactivate")
  public Reply<?> deactivate(@Named("id") Object id) {
    repository.deactivate(new ID(id));

    return Reply.saying().ok();
  }

  @Put
  @At("/:id/emergency")
  public Reply<?> updateEmergencyNumber(@Named("id") Object id, Request request) {
    NewEmergencyNumberDTO dto = request.read(NewEmergencyNumberDTO.class).as(Json.class);
    NewEmergencyNumber newNumber = adapt(id, dto);

    repository.updateEmergencyNumber(newNumber);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(new ID(id));

    return Reply.saying().ok();
  }

  private NewEmergencyNumber adapt(Object id, NewEmergencyNumberDTO dto) {
    return new NewEmergencyNumber(new ID(id), dto.value);
  }

  private Operator adapt(Object id, OperatorDTO dto) {
    return new Operator(new ID(id), dto.name, OperatorState.valueOf(dto.state), dto.description, dto.domainName, dto.friendlyName, dto.emergencyNumber, new IPv4(IPv4.Availability.valueOf(dto.ipV4)), new IPv6(IPv6.Availability.valueOf(dto.ipV6)));
  }

  private List<OperatorDTO> adapt(List<Operator> operators) {
    List<OperatorDTO> dtos = Lists.newArrayList();

    for (Operator operator : operators) {
      dtos.add(adapt(operator));
    }

    return dtos;
  }

  private OperatorDTO adapt(Operator operator) {
    return new OperatorDTO(operator.id.value, operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber, operator.ipV4.availability.name(), operator.ipV6.availability.name());
  }

  private List<AccessPointDTO> adaptToAPs(List<AccessPoint> aps) {
    List<AccessPointDTO> dtos = Lists.newArrayList();

    for (AccessPoint ap : aps) {
      dtos.add(new AccessPointDTO(ap.id.value, ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue), adapt(ap.geoLocation), adapt(ap.civicLocation), adapt(ap.capabilities)));
    }

    return dtos;
  }

  private CivicLocationDTO adapt(CivicLocation civicLocation) {
    return new CivicLocationDTO(civicLocation.country, civicLocation.city, civicLocation.street, civicLocation.streetNumber, civicLocation.postCode);
  }

  private VenueDTO adapt(Venue venue) {
    return new VenueDTO(venue.group.name, venue.type.name, adaptToVenueNameDTOs(venue.names));
  }

  private List<VenueNameDTO> adaptToVenueNameDTOs(List<VenueName> names) {
    List<VenueNameDTO> dtos = Lists.newArrayList();

    for (VenueName name : names) {
      dtos.add(new VenueNameDTO(name.name, name.language.code));
    }

    return dtos;
  }

  private GeoLocationDTO adapt(GeoLocation location) {
    return new GeoLocationDTO(location.latitude, location.longitude);
  }

  private NewOperator adapt(NewOperatorDTO dto) {
    return new NewOperator(dto.name, OperatorState.valueOf(dto.state), dto.description, dto.domainName, dto.friendlyName, dto.emergencyNumber, new IPv4(IPv4.Availability.valueOf(dto.ipV4)), new IPv6(IPv6.Availability.valueOf(dto.ipV6)));
  }

  private List<ID> adaptToIDs(List<Object> dtos) {
    List<ID> ids = Lists.newArrayList();

    for (Object dto : dtos) {
      ids.add(new ID(dto));
    }

    return ids;
  }

  private List<ServiceProviderDTO> adaptToServiceProviderDTOs(List<ServiceProvider> sps) {
    List<ServiceProviderDTO> dtos = Lists.newArrayList();

    for (ServiceProvider sp : sps) {
      dtos.add(new ServiceProviderDTO(sp.id.value, sp.name, sp.description, adaptToNetwork3GPPDTOs(sp.networks), sp.domainNames.values, adaptToConsortiumDTOs(sp.consortiums), adaptToNaiDTOs(sp.naiRealms)));
    }

    return dtos;
  }

  private List<RoamingConsortiumDTO> adaptToConsortiumDTOs(List<RoamingConsortium> consortiums) {
    List<RoamingConsortiumDTO> dtos = Lists.newArrayList();

    for (RoamingConsortium consortium : consortiums) {
      dtos.add(new RoamingConsortiumDTO(consortium.name, consortium.organizationID));
    }

    return dtos;
  }

  private List<NaiDTO> adaptToNaiDTOs(List<NAI> naiRealms) {
    List<NaiDTO> dtos = Lists.newArrayList();

    for (NAI nai : naiRealms) {
      dtos.add(new NaiDTO(nai.name, nai.encoding.name(), adaptToEapDTOs(nai.eaps)));
    }

    return dtos;
  }

  private List<EapDTO> adaptToEapDTOs(List<EAP> eaps) {
    List<EapDTO> dtos = Lists.newArrayList();

    for (EAP eap : eaps) {
      dtos.add(new EapDTO(eap.method.name(), adaptAuths(eap.auths)));
    }

    return dtos;
  }

  private List<AuthDTO> adaptAuths(List<Auth> auths) {
    List<AuthDTO> dtos = Lists.newArrayList();

    for (Auth auth : auths) {
      dtos.add(new AuthDTO(auth.info.name(), auth.type.name()));
    }

    return dtos;
  }

  private List<Network3GPPDTO> adaptToNetwork3GPPDTOs(List<Network3GPP> networks) {
    List<Network3GPPDTO> dtos = Lists.newArrayList();

    for (Network3GPP network : networks) {
      dtos.add(new Network3GPPDTO(network.name, network.mobileCountryCode, network.mobileNetworkCode));
    }

    return dtos;
  }

  private List<CapabilityDTO> adapt(CapabilityList list) {
    List<CapabilityDTO> dtos = Lists.newArrayList();

    for (Capability capability : list.values) {
      dtos.add(new CapabilityDTO(capability.id, capability.name));
    }
    return dtos;
  }
}
