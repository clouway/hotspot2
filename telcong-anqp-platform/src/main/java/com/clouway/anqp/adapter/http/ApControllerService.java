package com.clouway.anqp.adapter.http;

import com.clouway.anqp.ApController;
import com.clouway.anqp.ApControllerRepository;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewApController;
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
@At("/r/controllers")
public class ApControllerService {
  private final ApControllerRepository repository;

  @Inject
  public ApControllerService(ApControllerRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewApControllerDTO dto = request.read(NewApControllerDTO.class).as(Json.class);
    NewApController controller = adapt(dto);

    repository.create(controller);

    return Reply.saying().ok();
  }

  @Get
  public Reply<?> findAll() {
    List<ApController> controllers = repository.findAll();
    List<ApControllerDTO> dtos = adapt(controllers);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id") String id) {
    Optional<ApController> controller = repository.findById(id);

    if (!controller.isPresent()) {
      return Reply.saying().notFound();
    }

    ApControllerDTO dto = adapt(controller.get());

    return Reply.with(dto).as(Json.class);
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    ApControllerDTO dto = request.read(ApControllerDTO.class).as(Json.class);
    ApController controller = adapt(id, dto);

    repository.update(controller);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(id);

    return Reply.saying().ok();
  }

  private ApControllerDTO adapt(ApController controller) {
    return new ApControllerDTO(controller.id, controller.ip, controller.mac.value, controller.serialNumber, controller.model);
  }

  private NewApController adapt(NewApControllerDTO dto) {
    return new NewApController(dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model);
  }

  private ApController adapt(Object id, ApControllerDTO dto) {
    return new ApController(id, dto.ip, new MacAddress(dto.mac), dto.serialNumber, dto.model);
  }

  private List<ApControllerDTO> adapt(List<ApController> controllers) {
    List<ApControllerDTO> dtos = Lists.newArrayList();

    for (ApController controller : controllers) {
      dtos.add(new ApControllerDTO(controller.id, controller.ip, controller.mac.value, controller.serialNumber, controller.model));
    }

    return dtos;
  }
}