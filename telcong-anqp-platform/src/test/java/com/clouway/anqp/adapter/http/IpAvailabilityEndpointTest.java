package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IPv4;
import com.clouway.anqp.IPv4AvailabilityCatalog;
import com.clouway.anqp.IPv6;
import com.clouway.anqp.IPv6.Availability;
import com.clouway.anqp.IPv6AvailabilityCatalog;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.IPv4.Availability.PUBLIC;
import static com.clouway.anqp.adapter.http.ReplyMatchers.contains;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.hamcrest.MatcherAssert.assertThat;

public class IpAvailabilityEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private IPv4AvailabilityCatalog v4Catalog = context.mock(IPv4AvailabilityCatalog.class);
  private IPv6AvailabilityCatalog v6Catalog = context.mock(IPv6AvailabilityCatalog.class);

  private IpAvailabilityEndpoint service = new IpAvailabilityEndpoint(v4Catalog, v6Catalog);

  @Test
  public void findV4() throws Exception {
    final List<IPv4.Availability> availabilities = Lists.newArrayList(IPv4.Availability.PUBLIC);
    List<String> dto = Lists.newArrayList("PUBLIC");

    context.checking(new Expectations() {{
      oneOf(v4Catalog).findAll();
      will(returnValue(availabilities));

    }});

    Reply<?> reply = service.findV4();

    assertThat(reply, contains(dto));
    assertThat(reply, isOk());
  }

  @Test
  public void findV6() throws Exception {
    final List<IPv6.Availability> availabilities = Lists.newArrayList(IPv6.Availability.AVAILABLE);
    List<String> dto = Lists.newArrayList("AVAILABLE");

    context.checking(new Expectations() {{
      oneOf(v6Catalog).findAll();
      will(returnValue(availabilities));

    }});

    Reply<?> reply = service.findV6();

    assertThat(reply, contains(dto));
    assertThat(reply, isOk());
  }
}