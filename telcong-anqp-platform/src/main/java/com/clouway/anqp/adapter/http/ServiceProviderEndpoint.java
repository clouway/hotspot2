package com.clouway.anqp.adapter.http;

import com.clouway.anqp.ID;
import com.clouway.anqp.NewServiceProvider;
import com.clouway.anqp.ServiceProvider;
import com.clouway.anqp.ServiceProviderRepository;
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
@At("/r/service-providers")
public class ServiceProviderEndpoint {
  private final ServiceProviderRepository repository;

  @Inject
  public ServiceProviderEndpoint(ServiceProviderRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> create(Request request) {
    NewServiceProviderDTO dto = request.read(NewServiceProviderDTO.class).as(Json.class);
    NewServiceProvider provider = adapt(dto);

    repository.create(provider);

    return Reply.saying().ok();
  }

  @Get
  public Reply<?> findAll() {
    List<ServiceProvider> providers = repository.findAll();

    List<ServiceProviderDTO> dtos = adapt(providers);

    return Reply.with(dtos).as(Json.class).ok();
  }

  @Get
  @At("/:id")
  public Reply<?> findById(@Named("id") String id) {
    Optional<ServiceProvider> provider = repository.findById(new ID(id));

    if (!provider.isPresent()) {
      return Reply.saying().notFound();
    }

    ServiceProviderDTO dto = adapt(provider.get());

    return Reply.with(dto).as(Json.class).ok();
  }

  @Put
  @At("/:id")
  public Reply<?> update(@Named("id") Object id, Request request) {
    ServiceProviderDTO dto = request.read(ServiceProviderDTO.class).as(Json.class);
    ServiceProvider provider = adapt(id, dto);

    repository.update(provider);

    return Reply.saying().ok();
  }

  @Delete
  @At("/:id")
  public Reply<?> delete(@Named("id") String id) {
    repository.delete(new ID(id));

    return Reply.saying().ok();
  }

  private NewServiceProvider adapt(NewServiceProviderDTO dto) {
    return new NewServiceProvider(dto.name, dto.description);
  }

  private ServiceProviderDTO adapt(ServiceProvider provider) {
    return new ServiceProviderDTO(provider.id, provider.name, provider.description);
  }

  private List<ServiceProviderDTO> adapt(List<ServiceProvider> serviceProviders) {
    List<ServiceProviderDTO> dtos = Lists.newArrayList();

    for (ServiceProvider provider : serviceProviders) {
      dtos.add(new ServiceProviderDTO(provider.id, provider.name, provider.description));
    }

    return dtos;
  }

  private ServiceProvider adapt(Object id, ServiceProviderDTO dto) {
    return new ServiceProvider(new ID(id), dto.name, dto.description);
  }
}
