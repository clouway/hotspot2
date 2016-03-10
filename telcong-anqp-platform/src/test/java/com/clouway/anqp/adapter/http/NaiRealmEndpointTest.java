package com.clouway.anqp.adapter.http;

import com.clouway.anqp.Encoding;
import com.clouway.anqp.EncodingCatalog;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Encoding.UTF_8;
import static com.clouway.anqp.adapter.http.ReplyMatchers.contains;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class NaiRealmEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private EncodingCatalog catalog = context.mock(EncodingCatalog.class);

  private NaiRealmEndpoint endpoint = new NaiRealmEndpoint(catalog);

  @Test
  public void fetchEncodings() throws Exception {
    final List<Encoding> encodings= Lists.newArrayList(UTF_8);
    final List<String> list = Lists.newArrayList("UTF_8");

    context.checking(new Expectations() {{
      oneOf(catalog).findAll();
      will(returnValue(encodings));
    }});

    Reply<?> reply = endpoint.fetchEncodings();

    assertThat(reply, contains(list));
    assertThat(reply, isOk());
  }
}