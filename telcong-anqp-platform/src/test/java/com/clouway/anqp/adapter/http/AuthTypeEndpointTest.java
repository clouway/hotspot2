package com.clouway.anqp.adapter.http;

import com.clouway.anqp.AuthenticationType;
import com.clouway.anqp.AuthenticationTypeFinder;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.junit.Assert.*;

/**
 */
public class AuthTypeEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private AuthenticationTypeFinder authenticationFinder = context.mock(AuthenticationTypeFinder.class);

  private AuthTypeEndpoint service = new AuthTypeEndpoint(authenticationFinder);

  @Test
  public void findAll() throws Exception {
    final List<AuthenticationType> authTypes = Lists.newArrayList(new AuthenticationType(1, "One"), new AuthenticationType(2, "Two"));
    final List<AuthenticationTypeDTO> dtos = Lists.newArrayList(new AuthenticationTypeDTO(1, "One"), new AuthenticationTypeDTO(2, "Two"));

    context.checking(new Expectations() {{
      oneOf(authenticationFinder).findAll();
      will(returnValue(authTypes));
    }});

    Reply<?> reply = service.findAll();

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }
}