package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.IpType.PUBLIC;
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
    final NewOperator operator = new NewOperator("name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC);
    final NewOperatorDTO dto = new NewOperatorDTO("name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", "PUBLIC");

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(operator)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    final Operator operator = new Operator(new ID("id1"), "name1", OperatorState.ACTIVE, "description1", "domainName1", "friendlyName1", "emergencyNumber", PUBLIC);
    final Operator anotherOperator = new Operator(new ID("id2"), "name2", OperatorState.ACTIVE, "description2", "domainName2", "friendlyName2", "emergencyNumber", PUBLIC);
    final List<Operator> operators = Lists.newArrayList(operator, anotherOperator);

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(operators));
    }});

    Reply<?> reply = service.findAll();
    List<OperatorDTO> expected = Lists.newArrayList(
            new OperatorDTO("id1", "name1", "ACTIVE", "description1", "domainName1", "friendlyName1", "emergencyNumber", "PUBLIC"),
            new OperatorDTO("id2", "name2", "ACTIVE", "description2", "domainName2", "friendlyName2", "emergencyNumber", "PUBLIC")
    );

    assertThat(reply, containsValue(expected));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    final Operator operator = new Operator(new ID("id"), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC);

    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(new ID("id"))));
      will(returnValue(Optional.of(operator)));
    }});

    Reply<?> replay = service.findById("id");
    OperatorDTO expected = new OperatorDTO("id", "name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", "PUBLIC");

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
    final Operator operator = new Operator(new ID(1), "name", OperatorState.ACTIVE, "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC);
    final OperatorDTO dto = new OperatorDTO(1, "name", "ACTIVE", "description", "domainName", "friendlyName", "emergencyNumber", PUBLIC.name());

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
      oneOf(repository).assignAccessPoints(with(matching(operID)) , with(matching(apIDs)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.assignAccessPoints("operID", request);

    assertThat(reply, isOk());
  }


  @Test
  public void findAccessPoints() throws Exception {
    Venue venue = new Venue(new VenueGroup("group"), new VenueType("type"), Lists.newArrayList(new VenueName("info", new Language("en"))));
    VenueDTO venueDTO = new VenueDTO("group", "type", Lists.newArrayList(new VenueNameDTO("info","en")));

    final List<AccessPoint> aps = Lists.newArrayList(new AccessPoint(new ID("apID"), "ip", new MacAddress("aa:bb"), "sn", "model", venue));
    final List<AccessPointDTO> dtos = Lists.newArrayList(new AccessPointDTO("apID", "ip", "aa:bb", "sn", "model", venueDTO));
    final ID operID = new ID("operID");

    context.checking(new Expectations() {{
      oneOf(repository).findAccessPoints(with(matching(operID)));
      will(returnValue(aps));
    }});

    Reply<?> reply = service.findAccessPoints("operID");

    assertThat(reply, containsValue(dtos));
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
