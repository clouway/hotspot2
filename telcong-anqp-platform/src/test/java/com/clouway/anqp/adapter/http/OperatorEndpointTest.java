package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.clouway.anqp.Capability;
import com.clouway.anqp.IPv4.Availability;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Type.EXPANDED_EAP_METHOD_SUBFIELD;
import static com.clouway.anqp.EAP.Method.EAP_SIM;
import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isNotFound;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static com.clouway.anqp.adapter.http.RequestFactory.makeRequestThatContains;
import static com.clouway.anqp.util.matchers.EqualityMatchers.matching;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class OperatorEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private OperatorRepository repository = context.mock(OperatorRepository.class);

  private OperatorEndpoint service = new OperatorEndpoint(repository);

  @Test
  public void create() throws Exception {
    IPv4 iPv4 = new IPv4(Availability.UNKNOWN);
    IPv6 iPv6 = new IPv6(IPv6.Availability.UNKNOWN);
    final NewOperator operator = new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", iPv4, iPv6);
    final NewOperatorDTO dto = new NewOperatorDTO("name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", "UNKNOWN", "UNKNOWN");

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(operator)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    IPv4 iPv4 = new IPv4(Availability.UNKNOWN);
    IPv6 iPv6 = new IPv6(IPv6.Availability.UNKNOWN);
    final Operator operator = new Operator(new ID("id1"), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber", iPv4, iPv6);
    final Operator anotherOperator = new Operator(new ID("id2"), "name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber", iPv4, iPv6);
    final List<Operator> operators = Lists.newArrayList(operator, anotherOperator);

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(operators));
    }});

    Reply<?> reply = service.findAll();
    List<OperatorDTO> expected = Lists.newArrayList(
            new OperatorDTO("id1", "name1", "ACTIVE", "description1", "domainName1", "friendlyName1", "emergencyNumber", "UNKNOWN", "UNKNOWN"),
            new OperatorDTO("id2", "name2", "ACTIVE", "description2", "domainName2", "friendlyName2", "emergencyNumber", "UNKNOWN", "UNKNOWN")
    );

    assertThat(reply, containsValue(expected));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    IPv4 iPv4 = new IPv4(Availability.UNKNOWN);
    IPv6 iPv6 = new IPv6(IPv6.Availability.UNKNOWN);

    final Operator operator = new Operator(new ID("id"), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", iPv4, iPv6);

    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(new ID("id"))));
      will(returnValue(Optional.of(operator)));
    }});

    Reply<?> replay = service.findById("id");
    OperatorDTO expected = new OperatorDTO("id", "name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", "UNKNOWN", "UNKNOWN");

    assertThat(replay, containsValue(expected));
    assertThat(replay, isOk());
  }

  @Test
  public void findByUnknownId() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(new ID("id"))));
      will(returnValue(Optional.absent()));
    }});

    Reply<?> replay = service.findById("id");

    assertThat(replay, isNotFound());
  }

  @Test
  public void update() throws Exception {
    IPv4 iPv4 = new IPv4(Availability.UNKNOWN);
    IPv6 iPv6 = new IPv6(IPv6.Availability.UNKNOWN);

    final Operator operator = new Operator(new ID(1), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", iPv4, iPv6);
    final OperatorDTO dto = new OperatorDTO(1, "name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", "UNKNOWN","UNKNOWN");

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(operator)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.update(1, request);

    assertThat(reply, isOk());
  }

  @Test
  public void assignAccessPoints() throws Exception {
    final List<String> dto = Lists.newArrayList("apID1", "apID2", "apID3");
    final List<ID> apIDs = Lists.newArrayList(new ID("apID1"), new ID("apID2"), new ID("apID3"));
    final ID operID = new ID("operID");

    context.checking(new Expectations() {{
      oneOf(repository).assignAccessPoints(with(matching(operID)), with(matching(apIDs)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.assignAccessPoints("operID", request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAccessPoints() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");

    GeoLocation geo = new GeoLocation(65.65656565, 75.75757575);
    GeoLocationDTO geoDTO = new GeoLocationDTO(65.65656565, 75.75757575);

    final AccessPoint ap = new AccessPoint(new ID("id"), "ip", new MacAddress("aa:bb"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "a"))));
    final AccessPointDTO apDTO = new AccessPointDTO("id", "ip", "aa:bb", "sn", "model", venueDTO, geoDTO, civicDTO, Lists.newArrayList(new CapabilityDTO(256, "a")));
    final List<AccessPoint> aps = Lists.newArrayList(ap);
    final List<AccessPointDTO> dtos = Lists.newArrayList(apDTO);
    final ID operID = new ID("id");

    context.checking(new Expectations() {{
      oneOf(repository).findAccessPoints(with(matching(operID)));
      will(returnValue(aps));
    }});

    Reply<?> reply = service.findAccessPoints("id");

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void assignServiceProviders() throws Exception {
    final List<String> dto = Lists.newArrayList("spID1", "spID2", "spID3");
    final List<ID> spIDs = Lists.newArrayList(new ID("spID1"), new ID("spID2"), new ID("spID3"));
    final ID operID = new ID("operID");

    context.checking(new Expectations() {{
      oneOf(repository).assignServiceProviders(with(matching(operID)), with(matching(spIDs)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.assignServiceProviders("operID", request);

    assertThat(reply, isOk());
  }

  @Test
  public void removeServiceProviders() throws Exception {
    final List<String> dto = Lists.newArrayList("spID1", "spID2", "spID3");
    final List<ID> spIDs = Lists.newArrayList(new ID("spID1"), new ID("spID2"), new ID("spID3"));
    final ID operID = new ID("operID");

    context.checking(new Expectations() {{
      oneOf(repository).removeServiceProviders(with(matching(operID)), with(matching(spIDs)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.removeServiceProviders("operID", request);

    assertThat(reply, isOk());
  }

  @Test
  public void findServiceProviders() throws Exception {
    final List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "123", "33"));
    final DomainNameList list = new DomainNameList(Lists.newArrayList("dName"));
    final List<RoamingConsortium> consortiums = Lists.newArrayList(new RoamingConsortium("name", "0xAAFFBB"));
    final List<Auth> auths = Lists.newArrayList(new Auth(CREDENTIAL_TYPE, EXPANDED_EAP_METHOD_SUBFIELD));
    final List<EAP> eaps = Lists.newArrayList(new EAP(EAP_SIM, auths));
    final List<NAI> nais = Lists.newArrayList(new NAI("name", Encoding.UTF_8, eaps));
    final ServiceProvider sp = new ServiceProvider(new ID("id"), "name", "descr", networks, list, consortiums, nais);
    final List<ServiceProvider> sps = Lists.newArrayList(sp);

    final List<Network3GPPDTO> networkDTOs = Lists.newArrayList(new Network3GPPDTO("name", "123", "33"));
    final List<String> listDTO = Lists.newArrayList("dName");
    final List<RoamingConsortiumDTO> consortiumDTOs = Lists.newArrayList(new RoamingConsortiumDTO("name", "0xAAFFBB"));
    final List<AuthDTO> authDTOs = Lists.newArrayList(new AuthDTO("CREDENTIAL_TYPE", "EXPANDED_EAP_METHOD_SUBFIELD"));
    final List<EapDTO> eapDTOs = Lists.newArrayList(new EapDTO("EAP_SIM", authDTOs));
    final List<NaiDTO> naiDTOs = Lists.newArrayList(new NaiDTO("name", Encoding.UTF_8.name(), eapDTOs));
    final ServiceProviderDTO spDTO = new ServiceProviderDTO(new ID("id"), "name", "descr", networkDTOs, listDTO, consortiumDTOs, naiDTOs);
    final List<ServiceProviderDTO> spDTOs = Lists.newArrayList(spDTO);

    final ID operID = new ID("id");

    context.checking(new Expectations() {{
      oneOf(repository).findServiceProviders(with(matching(operID)));
      will(returnValue(sps));
    }});

    Reply<?> reply = service.findServiceProviders("id");

    assertThat(reply, containsValue(spDTOs));
    assertThat(reply, isOk());
  }

  @Test
  public void activate() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).activate(with(matching(new ID("id"))));
    }});

    Reply<?> reply = service.activate("id");

    assertThat(reply, isOk());
  }

  @Test
  public void deactivate() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).deactivate(with(matching(new ID("id"))));
    }});

    Reply<?> reply = service.deactivate("id");

    assertThat(reply, isOk());
  }

  @Test
  public void updateEmergencyNumber() throws Exception {
    final NewEmergencyNumber newNumber = new NewEmergencyNumber(new ID("id"), "112");
    final NewEmergencyNumberDTO dto = new NewEmergencyNumberDTO("112");

    context.checking(new Expectations() {{
      oneOf(repository).updateEmergencyNumber(with(matching(newNumber)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.updateEmergencyNumber("id", request);

    assertThat(reply, isOk());
  }

  @Test
  public void delete() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).delete(with(matching(new ID("id"))));
    }});

    Reply<?> reply = service.delete("id");

    assertThat(reply, isOk());
  }
}
