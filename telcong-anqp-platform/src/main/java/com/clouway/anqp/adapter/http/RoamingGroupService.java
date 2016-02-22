package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
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
@At("/r/roaming-groups")
public class RoamingGroupService {
  private final RoamingGroupRepository repository;

  @Inject
  public RoamingGroupService(RoamingGroupRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewRoamingGroupDTO dto = request.read(NewRoamingGroupDTO.class).as(Json.class);
    NewRoamingGroup group = adapt(dto);

    repository.create(group);

    return Reply.saying().ok();
  }

  @Get
  public Reply<?> findAll() {
    List<RoamingGroup> rgs = repository.findAll();
    List<RoamingGroupDTO> dtos = adapt(rgs);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id") String id) {
    Optional<RoamingGroup> group = repository.findById(new ID(id));

    if (!group.isPresent()) {
      return Reply.saying().notFound();
    }

    RoamingGroupDTO dto = adapt(group.get());

    return Reply.with(dto).as(Json.class).ok();
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    RoamingGroupRequestDTO dto = request.read(RoamingGroupRequestDTO.class).as(Json.class);
    RoamingGroupRequest group = adapt(id, dto);

    repository.update(group);

    return Reply.saying().ok();
  }

  @Put
  @At("/:id/operators/assign")
  public Reply<?> assignOperators(@Named("id")Object id, Request request) {
    List<Object> dtos = request.read(new TypeLiteral<List<Object>>() {}).as(Json.class);
    List<ID> ids = adaptToID(dtos);

    repository.assignOperators(new ID(id), ids);

    return Reply.saying().ok();
  }

  @Put
  @At("/:id/operators/remove")
  public Reply<?> removeOperators(@Named("id")Object id, Request request) {
    List<Object> dtos = request.read(new TypeLiteral<List<Object>>() {}).as(Json.class);
    List<ID> ids =  adaptToID(dtos);

    repository.removeOperators(new ID(id), ids);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(new ID(id));

    return Reply.saying().ok();
  }

  private NewRoamingGroup adapt(NewRoamingGroupDTO dto) {
    return new NewRoamingGroup(dto.name, dto.description, RoamingGroupType.valueOf(dto.type));
  }

  private List<ID> adaptToID(List<Object> dtos) {
    List<ID> ids = Lists.newArrayList();

    for (Object dto : dtos) {
      ids.add(new ID(dto));
    }

    return ids;
  }

  private List<RoamingGroupDTO> adapt(List<RoamingGroup> groups) {
    List<RoamingGroupDTO> dtos = Lists.newArrayList();

    for (RoamingGroup rg : groups) {
      dtos.add(adapt(rg));
    }

    return dtos;
  }

  private RoamingGroupDTO adapt(RoamingGroup group) {
    return new RoamingGroupDTO(group.id.value, group.name, group.description, group.type.name(), adaptToOperatorDTOs(group.operators));
  }

  private List<OperatorDTO> adaptToOperatorDTOs(List<Operator> operators) {
    List<OperatorDTO> dtos = Lists.newArrayList();

    for (Operator operator : operators) {
      dtos.add(new OperatorDTO(operator.id.value, operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber));
    }

    return dtos;
  }

  private RoamingGroupRequest adapt(Object roamingGroupID, RoamingGroupRequestDTO dto) {
    return new RoamingGroupRequest(new ID(roamingGroupID), dto.name, dto.description, RoamingGroupType.valueOf(dto.type));
  }
}
