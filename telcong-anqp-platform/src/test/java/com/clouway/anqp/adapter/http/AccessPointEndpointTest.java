package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.clouway.anqp.Capability;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.AccessPointBuilder.newAp;
import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isNotFound;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static com.clouway.anqp.adapter.http.RequestFactory.makeRequestThatContains;
import static com.clouway.anqp.util.matchers.EqualityMatchers.matching;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class AccessPointEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final AccessPointRepository repository = context.mock(AccessPointRepository.class);
  private final CapabilityCatalog catalog = context.mock(CapabilityCatalog.class);
  private final Request request = context.mock(Request.class);

  private AccessPointEndpoint service = new AccessPointEndpoint(repository, catalog);

  @Test
  public void create() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");

    GeoLocation geo = new GeoLocation(65.65656565, 75.75757575);
    GeoLocationDTO geoDTO = new GeoLocationDTO(65.65656565, 75.75757575);

    final NewAccessPoint ap = new NewAccessPoint(new ID("operatorID"), "ip", new MacAddress("aa:bb"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"), new Capability(257, "ANQP Capability list"))));
    final NewAccessPointDTO apDTO = new NewAccessPointDTO("operatorID", "ip", "aa:bb", "sn", "model", venueDTO, geoDTO, civicDTO, Lists.newArrayList(256, 257));

    context.checking(new Expectations() {{
      oneOf(catalog).findByIds(Lists.newArrayList(256, 257));
      will(returnValue(new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"), new Capability(257, "ANQP Capability list")))));

      oneOf(repository).create(with(matching(ap)));
    }});

    Request request = makeRequestThatContains(apDTO);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");

    GeoLocation geo = new GeoLocation(65.65656565, 75.75757575);
    GeoLocationDTO geoDTO = new GeoLocationDTO(65.65656565, 75.75757575);

    final AccessPoint ap = new AccessPoint(new ID(1), "ip", new MacAddress("aa:bb"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "a"))));
    final AccessPointDTO apDTO = new AccessPointDTO(1, "ip", "aa:bb", "sn", "model", venueDTO, geoDTO, civicDTO, Lists.newArrayList(new CapabilityDTO(256, "a")));

    final List<AccessPoint> aps = Lists.newArrayList(ap);
    final List<AccessPointDTO> dtos = Lists.newArrayList(apDTO);

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(aps));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");

    GeoLocation geo = new GeoLocation(65.65656565, 75.75757575);
    GeoLocationDTO geoDTO = new GeoLocationDTO(65.65656565, 75.75757575);

    final AccessPoint ap = new AccessPoint(new ID("id"), "ip", new MacAddress("aa:bb"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "a"))));
    final AccessPointDTO dto = new AccessPointDTO("id", "ip", "aa:bb", "sn", "model", venueDTO, geoDTO, civicDTO, Lists.newArrayList(new CapabilityDTO(256, "a")));

    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.of(ap)));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, containsValue(dto));
    assertThat(reply, isOk());
  }

  @Test
  public void findByUnknownId() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.absent()));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, isNotFound());
  }

  @Test
  public void findQueryList() throws Exception {
    final QueryList queryList = new QueryList(1);

    final String apId = "apId";

    context.checking(new Expectations() {{
      oneOf(repository).findQueryList(apId);
      will(returnValue(queryList));
    }});

    QueryListDTO dto = new QueryListDTO(Lists.newArrayList(1));

    Reply<?> reply = service.findQueryList(apId);

    assertThat(reply, containsValue(dto));
    assertThat(reply, isOk());
  }

  @Test
  public void findCapabilitiesById() throws Exception {
    final CapabilityList capabilities = new CapabilityList(Lists.newArrayList(new Capability(1, "capability1")));

    List<CapabilityDTO> dtos = Lists.newArrayList(new CapabilityDTO(1, "capability1"));

    context.checking(new Expectations() {{
      oneOf(repository).findCapabilityList(new ID("id1"));
      will(returnValue(capabilities));
    }});

    Reply<?> reply = service.findCapabilityList("id1");

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void fetchEmergencyURI() throws Exception {
    final AccessPoint ap = newAp().build();

    context.checking(new Expectations() {{
      oneOf(request).header("Host");
      will(returnValue("localhost:8080"));

      oneOf(repository).findById("id1");
      will(returnValue(Optional.of(ap)));
    }});

    Reply<?> reply = service.fetchEmergencyAlertURI("id1", request);

    assertThat(reply, containsValue("localhost:8080/r/emergency-alerts"));
  }

  @Test
  public void fetchEmergencyURIForMissingAP() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById("id1");
      will(returnValue(Optional.absent()));
    }});

    Reply<?> reply = service.fetchEmergencyAlertURI("id1", request);

    assertThat(reply, isNotFound());
  }

  @Test
  public void fetchLocationUri() throws Exception {
    final AccessPoint ap = newAp().build();

    context.checking(new Expectations() {{
      oneOf(repository).findById("id1");
      will(returnValue(Optional.of(ap)));

      oneOf(request).header("Host");
      will(returnValue("localhost:8080"));
    }});

    Reply<?> reply = service.fetchLocationURI("id1", request);

    String locationURI = "localhost:8080/r/aps/id1/location";

    assertThat(reply, containsValue(locationURI));
    assertThat(reply, isOk());
  }

  @Test
  public void fetchLocationUriForMissingAP() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById("id1");
      will(returnValue(Optional.absent()));

      never(request).header("Host");
    }});

    Reply<?> reply = service.fetchLocationURI("id1", request);

    assertThat(reply, isNotFound());
  }

  @Test
  public void fetchLocation() throws Exception {
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");
    GeoLocationDTO geoDTO = new GeoLocationDTO(10.0, 20.0);

    CivicLocation civicLocation = new CivicLocation("country", "city", "street", "number", "postCode");
    GeoLocation geoLocation = new GeoLocation(10.0, 20.0);

    final AccessPoint ap = newAp().civicLocation(civicLocation).geoLocation(geoLocation).build();

    context.checking(new Expectations() {{
      oneOf(repository).findById("id1");
      will(returnValue(Optional.of(ap)));
    }});

    ApLocationDTO dto = new ApLocationDTO(civicDTO, geoDTO);

    Reply<?> reply = service.fetchLocation("id1");

    assertThat(reply, containsValue(dto));
  }

  @Test
  public void fetchLocationForMissingAp() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById("id1");
      will(returnValue(Optional.absent()));
    }});

    Reply<?> reply = service.fetchLocation("id1");

    assertThat(reply, isNotFound());
  }

  @Test
  public void update() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));

    CivicLocation civic = new CivicLocation("country", "city", "street", "number", "postCode");
    CivicLocationDTO civicDTO = new CivicLocationDTO("country", "city", "street", "number", "postCode");

    GeoLocation geo = new GeoLocation(65.65656565, 75.75757575);
    GeoLocationDTO geoDTO = new GeoLocationDTO(65.65656565, 75.75757575);

    final AccessPointRequest accessPointRequest = new AccessPointRequest(new ID(1), "ip", new MacAddress("aa:bb"), "sn", "model", venue, geo, civic, new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List"))));

    context.checking(new Expectations() {{
      oneOf(catalog).findByIds(Lists.newArrayList(256));
      will(returnValue(new CapabilityList(Lists.newArrayList(new Capability(256, "ANQP Query List")))));

      oneOf(repository).update(with(matching(accessPointRequest)));
    }});

    final AccessPointRequestDTO dto = new AccessPointRequestDTO(1, "ip", "aa:bb", "sn", "model", venueDTO, geoDTO, civicDTO, Lists.newArrayList(256));

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.update(1, request);

    assertThat(reply, isOk());
  }

  @Test
  public void delete() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).delete(1);
    }});

    Reply<?> reply = service.delete(1);

    assertThat(reply, isOk());
  }
}