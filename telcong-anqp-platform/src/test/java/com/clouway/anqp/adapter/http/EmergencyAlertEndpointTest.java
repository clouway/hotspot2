package com.clouway.anqp.adapter.http;

import com.clouway.anqp.CapAlert;
import com.clouway.anqp.CapArea;
import com.clouway.anqp.CapInfo;
import com.clouway.anqp.CapMessage;
import com.clouway.anqp.CapResource;
import com.clouway.anqp.EmergencyAlertRepository;
import com.clouway.anqp.ID;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.junit.Assert.assertThat;


public class EmergencyAlertEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private EmergencyAlertRepository repository = context.mock(EmergencyAlertRepository.class);

  private EmergencyAlertEndpoint endpoint = new EmergencyAlertEndpoint(repository);

  @Test
  public void fetchAllAvailableMessages() throws Exception {
    final List<CapMessage> messages = Lists.newArrayList(
            new CapMessage(
                    new ID(1),
                    new CapAlert(Lists.newArrayList(new CapInfo(
                            Lists.newArrayList(new CapResource("resource1")),
                            Lists.newArrayList(new CapArea("area1"))
                    )))),
            new CapMessage(
                    new ID(2),
                    new CapAlert(Lists.newArrayList(new CapInfo(
                            Lists.newArrayList(new CapResource("resource2")),
                            Lists.newArrayList(new CapArea("area2"))
                    ))))
    );

    List<CapMessageDTO> dtos = Lists.newArrayList(
            new CapMessageDTO(
                    1,
                    new CapAlertDTO(Lists.newArrayList(new CapInfoDTO(
                            Lists.newArrayList("resource1"),
                            Lists.newArrayList("area1")
                    )))),
            new CapMessageDTO(
                    2,
                    new CapAlertDTO(Lists.newArrayList(new CapInfoDTO(
                            Lists.newArrayList("resource2"),
                            Lists.newArrayList("area2")
                    ))))
    );

    context.checking(new Expectations() {{
      oneOf(repository).findAll();
      will(returnValue(messages));
    }});

    Reply<?> reply = endpoint.fetchAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }
}