package com.clouway.anqp.adapter.http;

import com.clouway.anqp.IpTypeCatalog;
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

public class IpTypeServiceTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private IpTypeCatalog catalog = context.mock(IpTypeCatalog.class);
  private IpTypeService service = new IpTypeService(catalog);

  @Test
  public void happyPath() throws Exception {
    final List<String> typeNames = Lists.newArrayList("type1", "type2");

    context.checking(new Expectations() {{
      oneOf(catalog).getAll();
      will(returnValue(typeNames));
    }});

    Reply<?> reply = service.getAll();

    assertThat(reply, containsValue(typeNames));
    assertThat(reply, isOk());
  }
}