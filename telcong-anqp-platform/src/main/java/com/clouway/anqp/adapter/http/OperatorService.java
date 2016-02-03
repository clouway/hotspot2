package com.clouway.anqp.adapter.http;

import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorRepository;
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
public class OperatorService {
  private final OperatorRepository repository;

  @Inject
  public OperatorService(OperatorRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewOperatorDTO dto = request.read(NewOperatorDTO.class).as(Json.class);
    NewOperator operator = adapt(dto);

    repository.create(operator);

    return Reply.saying().ok();
  }

  @Get
  public Reply<?> findAll() {
    List<Operator> operators = repository.findAll();

    List<OperatorDTO> dtos = adapt(operators);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id")String id) {
    Optional<Operator> operator = repository.findById(id);

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

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") Object id) {
    repository.delete(id);

    return Reply.saying().ok();
  }

  private Operator adapt(Object id, OperatorDTO dto) {
    return new Operator(id, dto.name, dto.description, dto.domainName, dto.friendlyName);
  }

  private List<OperatorDTO> adapt(List<Operator> operators) {
    List<OperatorDTO> dtos = Lists.newArrayList();

    for (Operator operator : operators) {
      dtos.add(adapt(operator));
    }

    return dtos;
  }

  private OperatorDTO adapt(Operator operator) {
    return new OperatorDTO(operator.id, operator.name, operator.description, operator.domainName, operator.friendlyName);
  }

  private NewOperator adapt(NewOperatorDTO dto) {
    return new NewOperator(dto.name, dto.description, dto.domainName, dto.friendlyName);
  }
}
