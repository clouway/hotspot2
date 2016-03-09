package com.clouway.anqp.adapter.http;

import com.clouway.anqp.DomainNameList;
import com.clouway.anqp.NAI;
import com.clouway.anqp.Network3GPP;
import com.clouway.anqp.RoamingConsortium;
import com.clouway.anqp.ID;
import com.clouway.anqp.NewServiceProvider;
import com.clouway.anqp.ServiceProvider;
import com.clouway.anqp.ServiceProviderRepository;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isNotFound;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static com.clouway.anqp.adapter.http.RequestFactory.makeRequestThatContains;
import static com.clouway.anqp.util.matchers.EqualityMatchers.matching;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class ServiceProviderEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private ServiceProviderRepository repository = context.mock(ServiceProviderRepository.class);

  private ServiceProviderEndpoint service = new ServiceProviderEndpoint(repository);

  @Test
  public void create() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName"));
    List<String> namesDTO = Lists.newArrayList("dName");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    List<RoamingConsortium> list = Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<RoamingConsortiumDTO> listDTO = Lists.newArrayList(new RoamingConsortiumDTO("name", "0xAAFFAA"));
    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));
    List<NaiDTO> naiDTOList = Lists.newArrayList(new NaiDTO("N/A"));

    NewServiceProviderDTO dto = new NewServiceProviderDTO("name", "description", networkDTOs, namesDTO, listDTO, naiDTOList);
    final NewServiceProvider provider = new NewServiceProvider(dto.name, dto.description, networks, names, list, naiList);

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(provider)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    final ID id = new ID("id");
    final DomainNameList names = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    final List<String> namesDTO = Lists.newArrayList("dName1", "dName2");

    List<RoamingConsortium> list = Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<RoamingConsortiumDTO> listDTO = Lists.newArrayList(new RoamingConsortiumDTO("name", "0xAAFFAA"));

    final List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    final List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));
    List<NaiDTO> naiDTOList = Lists.newArrayList(new NaiDTO("N/A"));
    
    final ServiceProvider provider = new ServiceProvider(id, "Mtel", "descr", networks, names, list, naiList);
    final ServiceProviderDTO dto = new ServiceProviderDTO(id.value, "Mtel", "descr", networkDTOs, namesDTO, listDTO, naiDTOList);
   

    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(id)));
      will(returnValue(Optional.of(provider)));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, containsValue(dto));
    assertThat(reply, isOk());
  }

  @Test
  public void findByUnknownId() throws Exception {
    final ID id = new ID("id");

    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(id)));
      will(returnValue(Optional.absent()));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, isNotFound());
  }

  @Test
  public void findAll() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    List<String> namesDTO = Lists.newArrayList("dName1", "dName2");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    List<RoamingConsortium> list = Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<RoamingConsortiumDTO> listDTO = Lists.newArrayList(new RoamingConsortiumDTO("name", "0xAAFFAA"));

    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));
    List<NaiDTO> naiDTOList = Lists.newArrayList(new NaiDTO("N/A"));

    ServiceProvider provider = new ServiceProvider(new ID("id1"), "name", "desc", networks, names, list, naiList);
    ServiceProviderDTO dto = new ServiceProviderDTO("id1", "name", "desc", networkDTOs, namesDTO, listDTO, naiDTOList);
    List<ServiceProviderDTO> dtos = Lists.newArrayList(dto);

    final List<ServiceProvider> providers = Lists.newArrayList(provider);

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(providers));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void update() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    List<String> namesDTO = Lists.newArrayList("dName1", "dName2");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    List<RoamingConsortium> list = Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<RoamingConsortiumDTO> listDTO = Lists.newArrayList(new RoamingConsortiumDTO("name", "0xAAFFAA"));

    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));
    List<NaiDTO> naiDTOList = Lists.newArrayList(new NaiDTO("N/A"));


    ServiceProviderDTO dto = new ServiceProviderDTO("1234", "name", "description", networkDTOs, namesDTO, listDTO, naiDTOList);
    final ServiceProvider provider = new ServiceProvider(new ID("id"), dto.name, dto.description, networks, names, list, naiList);

    Request request = makeRequestThatContains(dto);

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(provider)));
    }});

    Reply<?> reply = service.update("id", request);

    assertThat(reply, isOk());
  }

  @Test
  public void delete() throws Exception {
    final ID id = new ID("id");

    context.checking(new Expectations() {{
      oneOf(repository).delete(with(matching(id)));
    }});

    service.delete("id");
  }
}