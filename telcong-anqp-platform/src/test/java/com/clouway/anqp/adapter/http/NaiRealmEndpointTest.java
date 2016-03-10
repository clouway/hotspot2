package com.clouway.anqp.adapter.http;

import com.clouway.anqp.Auth.Type;
import com.clouway.anqp.AuthEntry;
import com.clouway.anqp.EAP.Method;
import com.clouway.anqp.EapAuthCatalog;
import com.clouway.anqp.EapMethodCatalog;
import com.clouway.anqp.Encoding;
import com.clouway.anqp.EncodingCatalog;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.EAP.Method.EAP_SIM;
import static com.clouway.anqp.EAP.Method.IDENTITY;
import static com.clouway.anqp.Encoding.UTF_8;
import static com.clouway.anqp.adapter.http.ReplyMatchers.contains;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class NaiRealmEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private EncodingCatalog encodingCatalog = context.mock(EncodingCatalog.class);
  private EapMethodCatalog methodCatalog = context.mock(EapMethodCatalog.class);
  private EapAuthCatalog authenticationCatalog = context.mock(EapAuthCatalog.class);

  private NaiRealmEndpoint endpoint = new NaiRealmEndpoint(encodingCatalog, methodCatalog, authenticationCatalog);

  @Test
  public void fetchEncodings() throws Exception {
    final List<Encoding> encodings = Lists.newArrayList(UTF_8);
    final List<String> list = Lists.newArrayList("UTF_8");

    context.checking(new Expectations() {{
      oneOf(encodingCatalog).findAll();
      will(returnValue(encodings));
    }});

    Reply<?> reply = endpoint.fetchEncodings();

    assertThat(reply, contains(list));
    assertThat(reply, isOk());
  }

  @Test
  public void fetchEapMethods() throws Exception {
    final List<Method> methods = Lists.newArrayList(EAP_SIM, IDENTITY);
    final List<String> dtos = Lists.newArrayList("EAP_SIM", "IDENTITY");

    context.checking(new Expectations() {{
      oneOf(methodCatalog).findAll();
      will(returnValue(methods));
    }});

    Reply<?> reply = endpoint.fetchEapMethods();

    assertThat(reply, contains(dtos));
    assertThat(reply, isOk());
  }

  @Test
  public void fetchEapAuthentications() throws Exception {
    final List<AuthEntry> auths = Lists.newArrayList(new AuthEntry(CREDENTIAL_TYPE, Type.EAP_SIM));
    final List<AuthEntryDTO> list = Lists.newArrayList(new AuthEntryDTO("CREDENTIAL_TYPE", Lists.newArrayList("EAP_SIM")));

    context.checking(new Expectations() {{
      oneOf(authenticationCatalog).findAll();
      will(returnValue(auths));
    }});

    Reply<?> reply = endpoint.fetchEapAuthentications();

    assertThat(reply, contains(list));
    assertThat(reply, isOk());
  }
}