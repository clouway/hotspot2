package com.clouway.anqp.adapter.http;

import com.clouway.anqp.DomainNameList;
import com.clouway.anqp.Network3GPP;
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
    DomainNameList domainNames = new DomainNameList(Lists.newArrayList("dName"));
    List<String> domainNamesDTO = Lists.newArrayList("dName");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    NewServiceProviderDTO dto = new NewServiceProviderDTO("name", "description", networkDTOs, domainNamesDTO);
    final NewServiceProvider provider = new NewServiceProvider(dto.name, dto.description, networks, domainNames);

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
    final DomainNameList domainNames = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    final List<String> domainNamesDTO = Lists.newArrayList("dName1", "dName2");

    final List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    final List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    final ServiceProvider provider = new ServiceProvider(id, "Mtel", "descr", networks, domainNames);
    final ServiceProviderDTO dto = new ServiceProviderDTO(id.value, "Mtel", "descr", networkDTOs, domainNamesDTO);

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
    DomainNameList domainNames = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    List<String> domainNamesDTO = Lists.newArrayList("dName1", "dName2");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    ServiceProvider provider = new ServiceProvider(new ID("id1"), "name", "desc", networks, domainNames);
    ServiceProviderDTO dto = new ServiceProviderDTO("id1", "name", "desc", networkDTOs, domainNamesDTO);
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
    String id = "id123";
    DomainNameList domainNames = new DomainNameList(Lists.newArrayList("dName1", "dName2"));
    List<String> domainNamesDTO = Lists.newArrayList("dName1", "dName2");

    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "359", "44"));
    List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "359", "44"));

    ServiceProviderDTO dto = new ServiceProviderDTO("id", "name", "description", networkDTOs, domainNamesDTO);
    final ServiceProvider provider = new ServiceProvider(new ID(id), dto.name, dto.description, networks, domainNames);

    Request request = makeRequestThatContains(dto);

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(provider)));
    }});

    Reply<?> reply = service.update(id, request);

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