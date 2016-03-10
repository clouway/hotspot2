package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.Encoding;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.Encoding.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class InMemoryEncodingCatalogTest {
  private InMemoryEncodingCatalog catalog = new InMemoryEncodingCatalog(UTF_8);

  @Test
  public void findAll() throws Exception {
    List<Encoding> encodings = catalog.findAll();

    List<Encoding> list = Lists.newArrayList(UTF_8);

    assertThat(encodings, is(list));
  }
}