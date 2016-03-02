package com.clouway.anqp.adapter.http;

import com.clouway.anqp.*;
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

import static com.clouway.anqp.IPv6.Availability.UNKNOWN;
import static com.clouway.anqp.RoamingGroupType.INTERNATIONAL;
import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isNotFound;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static com.clouway.anqp.adapter.http.RequestFactory.makeRequestThatContains;
import static com.clouway.anqp.util.matchers.EqualityMatchers.matching;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class RoamingGroupEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private RoamingGroupRepository repository = context.mock(RoamingGroupRepository.class);

  private RoamingGroupEndpoint service = new RoamingGroupEndpoint(repository);

  @Test
  public void create() throws Exception {
    final NewRoamingGroup rg = new NewRoamingGroup("name", "description", INTERNATIONAL);
    final NewRoamingGroupDTO dto = new NewRoamingGroupDTO("name", "description", "INTERNATIONAL");

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(rg)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    final List<RoamingGroup> rgs = Lists.newArrayList(
            new RoamingGroup(new ID(1), "name1", "description1", RoamingGroupType.NATIONAL, Lists.<Operator>newArrayList()),
            new RoamingGroup(new ID(2), "name2", "description2", RoamingGroupType.PERMANENT, Lists.<Operator>newArrayList())
    );

    final List<RoamingGroupDTO> dtos = Lists.newArrayList(
            new RoamingGroupDTO(1, "name1", "description1", "NATIONAL", Lists.<OperatorDTO>newArrayList()),
            new RoamingGroupDTO(2, "name2", "description2", "PERMANENT", Lists.<OperatorDTO>newArrayList())
    );

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(rgs));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    Operator operator = new Operator(new ID(1), "name", OperatorState.ACTIVE, "descr", "dName", "fName", "emergency", new IPv4(Availability.UNKNOWN), new IPv6(UNKNOWN));
    OperatorDTO operatorDTO = new OperatorDTO(1, "name", "ACTIVE", "descr", "dName", "fName", "emergency", "UNKNOWN", "UNKNOWN");
    final RoamingGroup rg = new RoamingGroup(new ID(1), "name", "description", RoamingGroupType.TROMBONE, Lists.newArrayList(operator));
    final RoamingGroupDTO dto = new RoamingGroupDTO(1, "name", "description", "TROMBONE", Lists.newArrayList(operatorDTO));

    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(new ID("id"))));
      will(returnValue(Optional.of(rg)));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, containsValue(dto));
    assertThat(reply, isOk());
  }

  @Test
  public void findByUnknownId() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById(with(matching(new ID("id"))));
      will(returnValue(Optional.absent()));
    }});

    Reply<?> reply = service.findById("id");

    assertThat(reply, isNotFound());
  }

  @Test
  public void update() throws Exception {
    final RoamingGroupRequest rg = new RoamingGroupRequest(new ID(1), "name", "description", RoamingGroupType.NATIONAL);
    final RoamingGroupRequestDTO dto = new RoamingGroupRequestDTO(1, "name", "description", "NATIONAL");

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(rg)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.update(1, request);

    assertThat(reply, isOk());
  }

  @Test
  public void assignOperators() throws Exception {
    final List<Object> dto = Lists.<Object>newArrayList(1, 2);
    final List<ID> ids = Lists.newArrayList(new ID(1), new ID(2));
    final ID groupID = new ID("groupID");

    context.checking(new Expectations() {{
      oneOf(repository).assignOperators(with(matching(groupID)), with(matching(ids)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.assignOperators("groupID", request);

    assertThat(reply, isOk());
  }

  @Test
  public void removeOperatorsFromRoamingGroup() throws Exception {
    final List<ID> ids = Lists.newArrayList(new ID(1), new ID(2));
    final List<Object> dto = Lists.<Object>newArrayList(1, 2);
    final ID groupID = new ID("groupID");

    context.checking(new Expectations() {{
      oneOf(repository).removeOperators(with(matching(groupID)), with(matching(ids)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.removeOperators("groupID", request);

    assertThat(reply, isOk());
  }

  @Test
  public void delete() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).delete(with(matching(new ID(1))));
    }});

    Reply<?> reply = service.delete(1);

    assertThat(reply, isOk());
  }
}
