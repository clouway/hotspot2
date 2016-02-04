package com.clouway.anqp.adapter.http;

import com.clouway.anqp.NewRoamingGroup;
import com.clouway.anqp.RoamingGroup;
import com.clouway.anqp.RoamingGroupRepository;
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
    Optional<RoamingGroup> group = repository.findById(id);

    if (!group.isPresent()) {
      return Reply.saying().notFound();
    }

    RoamingGroupDTO dto = adapt(group.get());

    return Reply.with(dto).as(Json.class).ok();
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    RoamingGroupDTO dto = request.read(RoamingGroupDTO.class).as(Json.class);
    RoamingGroup group = adapt(id, dto);

    repository.update(group);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(id);

    return Reply.saying().ok();
  }

  private RoamingGroupDTO adapt(RoamingGroup group) {
    return new RoamingGroupDTO(group.id, group.name, group.description, group.type);
  }

  private NewRoamingGroup adapt(NewRoamingGroupDTO dto) {
    return new NewRoamingGroup(dto.name, dto.description, dto.type);
  }

  private RoamingGroup adapt(Object id, RoamingGroupDTO dto) {
    return new RoamingGroup(id, dto.name, dto.description, dto.type);
  }

  private List<RoamingGroupDTO> adapt(List<RoamingGroup> groups) {
    List<RoamingGroupDTO> dtos = Lists.newArrayList();

    for (RoamingGroup rg : groups) {
      dtos.add(new RoamingGroupDTO(rg.id, rg.name, rg.description, rg.type));
    }

    return dtos;
  }
}
