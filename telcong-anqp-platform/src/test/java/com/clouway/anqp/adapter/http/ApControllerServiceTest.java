package com.clouway.anqp.adapter.http;

import com.clouway.anqp.ApController;
import com.clouway.anqp.ApControllerRepository;
import com.clouway.anqp.MacAddress;
import com.clouway.anqp.NewApController;
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
public class ApControllerServiceTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private ApControllerRepository repository = context.mock(ApControllerRepository.class);

  private ApControllerService service = new ApControllerService(repository);

  @Test
  public void create() throws Exception {
    final NewApController controller = new NewApController("ip", new MacAddress("aa:bb"), "sn", "model");
    final NewApControllerDTO dto = new NewApControllerDTO("ip", "aa:bb", "sn", "model");

    context.checking(new Expectations() {{
      oneOf(repository).create(with(matching(controller)));
    }});

    Request request = makeRequestThatContains(dto);
    Reply<?> reply = service.create(request);

    assertThat(reply, isOk());
  }

  @Test
  public void findAll() throws Exception {
    final List<ApController> controllers = Lists.newArrayList(
            new ApController(1, "ip1", new MacAddress("aa:bb"), "sn1", "model1"),
            new ApController(2, "ip2", new MacAddress("bb:aa"), "sn2", "model2")
    );

    final List<ApControllerDTO> dtos = Lists.newArrayList(
            new ApControllerDTO(1, "ip1", "aa:bb", "sn1", "model1"),
            new ApControllerDTO(2, "ip2", "bb:aa", "sn2", "model2")
    );

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(controllers));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void findById() throws Exception {
    final ApController controller = new ApController(1, "ip", new MacAddress("aa:bb"), "sn", "model");
    final ApControllerDTO dto = new ApControllerDTO(1, "ip", "aa:bb", "sn", "model");

    context.checking(new Expectations() {{
      oneOf(repository).findById("id");
      will(returnValue(Optional.of(controller)));
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
    final ApController controller = new ApController(1, "ip", new MacAddress("aa:bb"), "sn", "model");
    final ApControllerDTO dto = new ApControllerDTO(1, "ip", "aa:bb", "sn", "model");

    context.checking(new Expectations() {{
      oneOf(repository).update(with(matching(controller)));
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