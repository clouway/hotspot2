package com.clouway.anqp.adapter.http;

import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueItem;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.adapter.http.ReplyMatchers.containsValue;
import static com.clouway.anqp.adapter.http.ReplyMatchers.isOk;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class VenueServiceTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private VenueFinder venueFinder = context.mock(VenueFinder.class);

  private     VenueService service = new VenueService(venueFinder);

  @Test
  public void fetchAll() throws Exception {
    VenueGroup group = new VenueGroup("test");
    VenueTypeList types = new VenueTypeList(new VenueType("typeOne"), new VenueType("typeTwo"));

    final VenueItem item = new VenueItem(group, types);

    context.checking(new Expectations() {{
      oneOf(venueFinder).findAll();
      will(returnValue(Lists.newArrayList(item)));
    }});

    Reply<?> reply = service.fetchAll();

    List<VenueItemDTO> dtos = Lists.newArrayList(new VenueItemDTO("test", Lists.newArrayList("typeOne", "typeTwo")));

    assertThat(reply, containsValue(dtos));
    assertThat(reply, isOk());
  }
}