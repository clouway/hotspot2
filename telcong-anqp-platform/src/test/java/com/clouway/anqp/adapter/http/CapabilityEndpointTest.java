package com.clouway.anqp.adapter.http;

import com.clouway.anqp.Capability;
import com.clouway.anqp.CapabilityCatalog;
import com.clouway.anqp.CapabilityList;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static org.junit.Assert.assertThat;

public class CapabilityEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private CapabilityCatalog catalog = context.mock(CapabilityCatalog.class);

  private CapabilityEndpoint service = new CapabilityEndpoint(catalog);

  @Test
  public void findAll() throws Exception {
    final CapabilityList capabilities = new CapabilityList(Lists.newArrayList(new Capability(1, "capability1")));
    List<CapabilityDTO> dto = Lists.newArrayList(new CapabilityDTO(1, "capability1"));

    context.checking(new Expectations() {{
      oneOf(catalog).findAll();
      will(returnValue(capabilities));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dto));
  }
}