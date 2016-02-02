package com.clouway.anqp.adapter.http;

import com.clouway.anqp.AccessPoint;
import com.clouway.anqp.AccessPointRepository;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewAccessPoint;
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
public class AccessPointService {
  private final AccessPointRepository repository;

  @Inject
  public AccessPointService(AccessPointRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewAccessPointDTO dto = request.read(NewAccessPointDTO.class).as(Json.class);
    NewAccessPoint ap = adapt(dto);

    repository.create(ap);

    return Reply.saying().ok();
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
      return Reply.saying().notFound();
    }

    AccessPointDTO dto = adapt(ap.get());

    return Reply.with(dto).as(Json.class);
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    AccessPointDTO dto = request.read(AccessPointDTO.class).as(Json.class);
    AccessPoint ap = adapt(id, dto);

    repository.update(ap);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(id);

    return Reply.saying().ok();
  }

  private AccessPointDTO adapt(AccessPoint ap) {
    return new AccessPointDTO(ap.id, ap.ip, ap.mac.value, ap.serialNumber, ap.model);
  }

  private NewAccessPoint adapt(NewAccessPointDTO dto) {
    return new NewAccessPoint(dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model);
  }

  private AccessPoint adapt(Object id, AccessPointDTO dto) {
    return new AccessPoint(id, dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model);
  }

  private List<AccessPointDTO> adapt(List<AccessPoint> aps) {
    List<AccessPointDTO> dtos = Lists.newArrayList();

    for (AccessPoint ap : aps) {
      dtos.add(new AccessPointDTO(ap.id, ap.ip, ap.mac.value, ap.serialNumber, ap.model));
    }

    return dtos;
  }
}