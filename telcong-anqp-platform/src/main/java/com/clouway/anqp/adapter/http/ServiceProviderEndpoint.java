package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.clouway.anqp.Auth.Info;
import com.clouway.anqp.Auth.Type;
import com.clouway.anqp.EAP.Method;
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

    Object id = repository.create(provider);

    return Reply.with(new IdDTO(id)).as(Json.class).ok();
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

  private List<ServiceProviderDTO> adapt(List<ServiceProvider> serviceProviders) {
    List<ServiceProviderDTO> dtos = Lists.newArrayList();

    for (ServiceProvider provider : serviceProviders) {
      dtos.add(adapt(provider));
    }

    return dtos;
  }

  private ServiceProviderDTO adapt(ServiceProvider provider) {
    return new ServiceProviderDTO(provider.id.value, provider.name, provider.description, adaptToNetwork3GPPDTOs(provider.networks), provider.domainNames.values, adaptToConsortiomDTOs(provider.consortiums), adaptToNaiRealmDTOs(provider.naiRealms));
  }

  private List<Network3GPPDTO> adaptToNetwork3GPPDTOs(List<Network3GPP> networks) {
    List<Network3GPPDTO> dtos = Lists.newArrayList();

    for (Network3GPP network : networks) {
      dtos.add(new Network3GPPDTO(network.name, network.mobileCountryCode, network.mobileNetworkCode));
    }

    return dtos;
  }

  private List<RoamingConsortiumDTO> adaptToConsortiomDTOs(List<RoamingConsortium> consortiumList) {
    List<RoamingConsortiumDTO> dtos = Lists.newArrayList();

    for (RoamingConsortium consortium : consortiumList) {
      dtos.add(new RoamingConsortiumDTO(consortium.name, consortium.organizationID));
    }

    return dtos;
  }

  private List<NaiDTO> adaptToNaiRealmDTOs(List<NAI> nais) {
    List<NaiDTO> dtos = Lists.newArrayList();

    for (NAI nai : nais) {
      dtos.add(new NaiDTO(nai.name, nai.encoding.name(), addaptToEapDTOs(nai.eaps)));
    }
    return dtos;
  }

  private ServiceProvider adapt(Object id, ServiceProviderDTO dto) {
    DomainNameList domainNames = new DomainNameList(dto.domainNames);

    return new ServiceProvider(new ID(id), dto.name, dto.description, adaptToNetwork3GPPs(dto.networks), domainNames, adaptToConsortioms(dto.consortiums), adaptToNaiRealms(dto.naiRealms));
  }

  private NewServiceProvider adapt(NewServiceProviderDTO dto) {
    DomainNameList domainNames = new DomainNameList(dto.domainNames);

    return new NewServiceProvider(dto.name, dto.description, adaptToNetwork3GPPs(dto.networks), domainNames, adaptToConsortioms(dto.consortiums), adaptToNaiRealms(dto.naiRealms));
  }

  private List<Network3GPP> adaptToNetwork3GPPs(List<Network3GPPDTO> dtos) {
    List<Network3GPP> networks = Lists.newArrayList();

    for (Network3GPPDTO dto : dtos) {
      networks.add(new Network3GPP(dto.name, dto.mobileCountryCode, dto.mobileNetworkCode));
    }
    return networks;
  }

  private List<RoamingConsortium> adaptToConsortioms(List<RoamingConsortiumDTO> dtos) {
    List<RoamingConsortium> consortiumList = Lists.newArrayList();

    for (RoamingConsortiumDTO dto : dtos) {
      consortiumList.add(new RoamingConsortium(dto.name, dto.organizationID));
    }

    return consortiumList;
  }

  private List<NAI> adaptToNaiRealms(List<NaiDTO> dtos) {
    List<NAI> list = Lists.newArrayList();

    for (NaiDTO dto : dtos) {
      list.add(new NAI(dto.name, Encoding.valueOf(dto.encoding), adaptToEAPs(dto.eaps)));
    }

    return list;
  }

  private List<EAP> adaptToEAPs(List<EapDTO> dtos) {
    List<EAP> eaps = Lists.newArrayList();

    for (EapDTO dto : dtos) {
      eaps.add(new EAP(Method.valueOf(dto.method), adaptToAuths(dto.authentications)));
    }

    return eaps;
  }

  private List<Auth> adaptToAuths(List<AuthDTO> dtos) {
    List<Auth> auths = Lists.newArrayList();

    for (AuthDTO dto : dtos) {
      auths.add(new Auth(Info.valueOf(dto.info), Type.valueOf(dto.type)));
    }

    return auths;
  }

  private List<EapDTO> addaptToEapDTOs(List<EAP> eaps) {
    List<EapDTO> dtos = Lists.newArrayList();

    for (EAP eap : eaps) {
      dtos.add(new EapDTO(eap.method.name(), adaptToAuthDTOs(eap.auths)));
    }

    return dtos;
  }

  private List<AuthDTO> adaptToAuthDTOs(List<Auth> auths) {
    List<AuthDTO> dtos = Lists.newArrayList();

    for (Auth auth : auths) {
      dtos.add(new AuthDTO(auth.info.name(), auth.type.name()));
    }

    return dtos;
  }
}
