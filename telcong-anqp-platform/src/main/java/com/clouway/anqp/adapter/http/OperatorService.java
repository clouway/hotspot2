package com.clouway.anqp.adapter.http;

import com.clouway.anqp.ID;
import com.clouway.anqp.IpType;
import com.clouway.anqp.NewEmergencyNumber;
import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorRepository;
import com.clouway.anqp.OperatorState;
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

    return Reply.with("Successfully created operator").ok();
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
      return Reply.with("Not found operator with id " + id).notFound();
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

    return Reply.with("Successfully updated operator").ok();
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
    IpType ipType = IpType.valueOf(dto.ipType);

    return new Operator(new ID(id), dto.name, OperatorState.valueOf(dto.state), dto.description, dto.domainName, dto.friendlyName, dto.emergencyNumber, ipType);
  }

  private List<OperatorDTO> adapt(List<Operator> operators) {
    List<OperatorDTO> dtos = Lists.newArrayList();

    for (Operator operator : operators) {
      dtos.add(adapt(operator));
    }

    return dtos;
  }

  private OperatorDTO adapt(Operator operator) {
    return new OperatorDTO(operator.id.value, operator.name, operator.state.name(), operator.description, operator.domainName, operator.friendlyName, operator.emergencyNumber, operator.ipType.name());
  }

  private NewOperator adapt(NewOperatorDTO dto) {
    IpType ipType = IpType.valueOf(dto.ipType);

    return new NewOperator(dto.name, OperatorState.valueOf(dto.state), dto.description, dto.domainName, dto.friendlyName, dto.emergencyNumber, ipType);
  }
}
