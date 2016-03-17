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
@At("/r/aps")
public class AccessPointEndpoint {
  private final AccessPointRepository repository;
  private final CapabilityCatalog catalog;

  @Inject
  public AccessPointEndpoint(AccessPointRepository repository, CapabilityCatalog catalog) {
    this.repository = repository;
    this.catalog = catalog;
  }

  @Post
  public Reply<?> create(Request request) {
    NewAccessPointDTO dto = request.read(NewAccessPointDTO.class).as(Json.class);
    NewAccessPoint ap = adapt(dto);

    repository.create(ap);

    return Reply.with("Successfully created AP").ok();
  }

  @Get
  public Reply<?> findAll() {
    List<AccessPoint> aps = repository.findAll();
    List<AccessPointDTO> dtos = adapt(aps);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id") String id) {
    Optional<AccessPoint> ap = repository.findById(id);

    if (!ap.isPresent()) {
      return Reply.with("Not found AP with id " + id).notFound();
    }

    AccessPointDTO dto = adapt(ap.get());

    return Reply.with(dto).as(Json.class).ok();
  }

  @Get
  @At("/:id/query-list")
  public Reply<?> findQueryList(@Named("id") String id) {
    QueryList queryList = repository.findQueryList(id);

    QueryListDTO dto = new QueryListDTO(queryList.values);

    return Reply.with(dto).as(Json.class).ok();
  }

  @Get
  @At("/:id/capability-list")
  public Reply<?> findCapabilityList(@Named("id") String id) {
    CapabilityList capabilities = repository.findCapabilityList(new ID(id));

    List<CapabilityDTO> dtos = adapt(capabilities);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id/emergency-uri")
  public Reply<?> fetchEmergencyAlertURI(@Named("id") String id, Request request) {
    Optional<AccessPoint> ap = repository.findById(id);

    if (!ap.isPresent()) {
      return Reply.with("Not found AP with id " + id).notFound();
    }

    String host = request.header("Host");

    String emergencyURI = host + "/r/emergency-alerts";

    return Reply.with(emergencyURI).ok();
  }

  @Get
  @At("/:id/location-uri")
  public Reply<?> fetchLocationURI(@Named("id") String id, Request request) {
    Optional<AccessPoint> ap = repository.findById(id);

    if (!ap.isPresent()) {
      return Reply.with("Not found AP with id " + id).notFound();
    }

    String host = request.header("Host");

    String locationURI = host + "/r/aps/" + id + "/location";

    return Reply.with(locationURI).ok();
  }

  @Get
  @At("/:id/location")
  public Reply<?> fetchLocation(@Named("id") String id) {
    Optional<AccessPoint> ap = repository.findById(id);

    if (!ap.isPresent()) {
      return Reply.with("Not found AP with id " + id).notFound();
    }

    CivicLocation civicLocation = ap.get().civicLocation;
    GeoLocation geoLocation = ap.get().geoLocation;

    ApLocationDTO dto = new ApLocationDTO(adapt(civicLocation), adapt(geoLocation));

    return Reply.with(dto).as(Json.class).ok();
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    AccessPointRequestDTO dto = request.read(AccessPointRequestDTO.class).as(Json.class);

    AccessPointRequest apRequest = adapt(dto);

    repository.update(apRequest);

    return Reply.with("Successfully updated AP").ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(id);

    return Reply.saying().ok();
  }

  private List<AccessPointDTO> adapt(List<AccessPoint> aps) {
    List<AccessPointDTO> dtos = Lists.newArrayList();

    for (AccessPoint ap : aps) {
      dtos.add(adapt(ap));
    }

    return dtos;
  }

  private AccessPointDTO adapt(AccessPoint ap) {
    return new AccessPointDTO(ap.id.value, ap.ip, ap.mac.value, ap.serialNumber, ap.model, adapt(ap.venue), adapt(ap.geoLocation), adapt(ap.civicLocation), adapt(ap.capabilities));
  }

  private VenueDTO adapt(Venue venue) {
    List<VenueNameDTO> names = Lists.newArrayList();

    for (VenueName info : venue.names) {
      names.add(new VenueNameDTO(info.name, info.language.code));
    }

    return new VenueDTO(venue.group.name, venue.type.name, names);
  }

  private CivicLocationDTO adapt(CivicLocation civicLocation) {
    return new CivicLocationDTO(civicLocation.country, civicLocation.city, civicLocation.street, civicLocation.streetNumber, civicLocation.postCode);
  }

  private GeoLocationDTO adapt(GeoLocation location) {
    return new GeoLocationDTO(location.latitude, location.longitude);
  }

  private NewAccessPoint adapt(NewAccessPointDTO dto) {
    CapabilityList capabilities = catalog.findByIds(dto.capabilityIds);

    return new NewAccessPoint(new ID(dto.operatorId), dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model, adapt(dto.venue), adapt(dto.geoLocation), adapt(dto.civicLocation), capabilities);
  }

  private Venue adapt(VenueDTO dto) {
    List<VenueName> names = Lists.newArrayList();

    for (VenueNameDTO duple : dto.venueNames) {
      names.add(new VenueName(duple.name, new Language(duple.language)));
    }

    return new Venue(new VenueGroup(dto.group), new VenueType(dto.type), names);
  }

  private CivicLocation adapt(CivicLocationDTO address) {
    return new CivicLocation(address.country, address.city, address.street, address.streetNumber, address.postCode);
  }

  private GeoLocation adapt(GeoLocationDTO location) {
    return new GeoLocation(location.latitude, location.longitude);
  }

  private List<CapabilityDTO> adapt(CapabilityList capabilities) {
    List<CapabilityDTO> dtos = Lists.newArrayList();

    for (Capability capability : capabilities.values) {
      dtos.add(new CapabilityDTO(capability.id, capability.name));
    }

    return dtos;
  }

  private AccessPointRequest adapt(AccessPointRequestDTO dto) {
    VenueDTO venueDTO = dto.venue;

    CapabilityList capabilities = catalog.findByIds(dto.capabilityIds);

    return new AccessPointRequest(new ID(dto.id), dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model, adapt(venueDTO), adapt(dto.geoLocation), adapt(dto.civicLocation), capabilities);
  }
}