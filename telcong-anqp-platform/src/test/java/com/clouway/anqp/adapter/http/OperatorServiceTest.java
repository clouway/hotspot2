package com.clouway.anqp.adapter.http;

import com.clouway.anqp.NewOperator;
import com.clouway.anqp.Operator;
import com.clouway.anqp.OperatorRepository;
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
public class OperatorServiceTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private OperatorRepository repository = context.mock(OperatorRepository.class);

  private OperatorService service = new OperatorService(repository);

  @Test
  public void create() throws Exception {
    final NewOperator operator = new NewOperator("name", "description", "domainName", "friendlyName");
    final NewOperatorDTO dto = new NewOperatorDTO("name", "description", "domainName", "friendlyName");

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(operator)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    final Operator operator = new Operator("id1", "name1", "description1", "domainName1", "friendlyName1");
    final Operator anotherOperator = new Operator("id2", "name2", "description2", "domainName2", "friendlyName2");
    final List<Operator> operators = Lists.newArrayList(operator, anotherOperator);

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(operators));
    }});

    Reply<?> reply = service.findAll();
    List<OperatorDTO> expected = Lists.newArrayList(
            new OperatorDTO("id1", "name1", "description1", "domainName1", "friendlyName1"),
            new OperatorDTO("id2", "name2", "description2", "domainName2", "friendlyName2")
    );

    assertThat(reply, containsValue(expected));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    final Operator operator = new Operator("id", "name", "description", "domainName", "friendlyName");

    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.of(operator)));
    }});

    Reply<?> replay = service.findById("id");
    OperatorDTO expected = new OperatorDTO("id", "name", "description", "domainName", "friendlyName");

    assertThat(replay, containsValue(expected));
    assertThat(replay, isOk());
  }

  @Test
  public void findByUnknownId() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.absent()));
    }});

    Reply<?> replay = service.findById("id");

    assertThat(replay, isNotFound());
  }

  @Test
  public void update() throws Exception {
    final Operator operator = new Operator(1, "name", "description", "domainName", "friendlyName");
    final OperatorDTO dto = new OperatorDTO(1, "name", "description", "domainName", "friendlyName");

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(operator)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.update(1, request);

    assertThat(reply, isOk());
  }

  @Test
  public void delete() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).delete("id");
    }});

    Reply<?> reply = service.delete("id");

    assertThat(reply, isOk());
  }
}
