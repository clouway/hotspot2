package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.clouway.anqp.core.NotFoundException;
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
public class AccessPointEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final AccessPointRepository repository = context.mock(AccessPointRepository.class);

  private AccessPointEndpoint service = new AccessPointEndpoint(repository);

  @Test
  public void create() throws Exception {
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));
    Venue venue = new Venue(new VenueGroup(venueDTO.group), new VenueType(venueDTO.type), Lists.newArrayList(new VenueName("info", new Language("en"))));
    GeoLocation location = new GeoLocation(24.3456789, 45.5678934);
    GeoLocationDTO locationDTO = new GeoLocationDTO(24.3456789, 45.5678934);

    final NewAccessPoint ap = new NewAccessPoint(new ID("operatorId"), "ip", new MacAddress("aa:bb"), "sn", "model", venue, location);
    final NewAccessPointDTO dto = new NewAccessPointDTO("operatorId", "ip", "aa:bb", "sn", "model", venueDTO, locationDTO);

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(ap)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test(expected = NotFoundException.class)
  public void failedCreation() throws Exception {
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info", "en")));
    Venue venue = new Venue(new VenueGroup(venueDTO.group), new VenueType(venueDTO.type), Lists.newArrayList(new VenueName("info", new Language("en"))));
    GeoLocation location = new GeoLocation(24.3456789, 45.5678934);
    GeoLocationDTO locationDTO = new GeoLocationDTO(24.3456789, 45.5678934);


    final NewAccessPoint ap = new NewAccessPoint(new ID("operatorId"), "ip", new MacAddress("aa:bb"), "sn", "model", venue, location);
    final NewAccessPointDTO dto = new NewAccessPointDTO("operatorId", "ip", "aa:bb", "sn", "model", venueDTO, locationDTO);

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(ap)));
      will(throwException(new NotFoundException("missing operator")));
    }});

    Request request = makeRequestThatContains(dto);
    service.create(request);
  }

  @Test
  public void findAll() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info","en")));

    GeoLocation location = new GeoLocation(22.222222, 33.3333333);
    GeoLocationDTO locationDTO = new GeoLocationDTO(22.222222, 33.3333333);

    final List<AccessPoint> aps = Lists.newArrayList(
            new AccessPoint(new ID(1), "ip1", new MacAddress("aa:bb"), "sn1", "model1", venue, location),
            new AccessPoint(new ID(2), "ip2", new MacAddress("bb:aa"), "sn2", "model2", venue, location)
    );

    final List<AccessPointDTO> dtos = Lists.newArrayList(
            new AccessPointDTO(1, "ip1", "aa:bb", "sn1", "model1", venueDTO, locationDTO),
            new AccessPointDTO(2, "ip2", "bb:aa", "sn2", "model2", venueDTO, locationDTO)
    );

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
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info","en")));

    GeoLocation location = new GeoLocation(22.222222, 33.3333333);
    GeoLocationDTO locationDTO = new GeoLocationDTO(22.222222, 33.3333333);

    final AccessPoint ap = new AccessPoint(new ID(1), "ip", new MacAddress("aa:bb"), "sn", "model", venue, location);
    final AccessPointDTO dto = new AccessPointDTO(1, "ip", "aa:bb", "sn", "model", venueDTO, locationDTO);

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
  public void update() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info","en")));

    GeoLocation location = new GeoLocation(22.222222, 33.3333333);
    GeoLocationDTO locationDTO = new GeoLocationDTO(22.222222, 33.3333333);

    final AccessPoint ap = new AccessPoint(new ID(1), "ip", new MacAddress("aa:bb"), "sn", "model", venue, location);
    final AccessPointDTO dto = new AccessPointDTO(1, "ip", "aa:bb", "sn", "model", venueDTO, locationDTO);

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(ap)));
    }});

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