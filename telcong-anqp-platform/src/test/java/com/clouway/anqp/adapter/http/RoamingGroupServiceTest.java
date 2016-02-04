package com.clouway.anqp.adapter.http;

import com.clouway.anqp.NewRoamingGroup;
import com.clouway.anqp.RoamingGroup;
import com.clouway.anqp.RoamingGroupRepository;
import com.clouway.anqp.RoamingGroupType;
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
public class RoamingGroupServiceTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private RoamingGroupRepository repository = context.mock(RoamingGroupRepository.class);

  private RoamingGroupService service = new RoamingGroupService(repository);

  @Test
  public void create() throws Exception {
    final NewRoamingGroup rg = new NewRoamingGroup("name", "description", RoamingGroupType.INTERNATIONAL);
    final NewRoamingGroupDTO dto = new NewRoamingGroupDTO("name", "description", RoamingGroupType.INTERNATIONAL);

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
            new RoamingGroup(1, "name1", "description1", RoamingGroupType.NATIONAL),
            new RoamingGroup(2, "name2", "description2", RoamingGroupType.PERMANENT)
    );

    final List<RoamingGroupDTO> dtos = Lists.newArrayList(
            new RoamingGroupDTO(1, "name1", "description1", RoamingGroupType.NATIONAL),
            new RoamingGroupDTO(2, "name2", "description2", RoamingGroupType.PERMANENT)
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
    final RoamingGroup rg = new RoamingGroup(1, "name", "description", RoamingGroupType.TROMBONE);
    final RoamingGroupDTO dto = new RoamingGroupDTO(1, "name", "description", RoamingGroupType.TROMBONE);

    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.of(rg)));
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
  public void update() throws Exception {
    final RoamingGroup rg = new RoamingGroup(1, "name", "description", RoamingGroupType.REGIONAL);
    final RoamingGroupDTO dto = new RoamingGroupDTO(1, "name", "description", RoamingGroupType.REGIONAL);

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(rg)));
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
