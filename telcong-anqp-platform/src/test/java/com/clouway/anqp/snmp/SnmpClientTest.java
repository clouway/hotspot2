package com.clouway.anqp.snmp;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 */
public class SnmpClientTest {
  private FakeSnmpServer server = new FakeSnmpServer(2611);
  private SnmpClientFactory clientFactory = new CachedSnmpClientFactory(2611);

  @Before
  public void setUp() throws Exception {
    server.startAsync();
    server.awaitRunning();
  }

  @After
  public void tearDown() throws Exception {
    server.stopAsync();
    server.awaitTerminated();
  }

  @Test
  public void getValue() throws Exception {
    server.pretendThatConfigurationWorkflowWillBe(
            new RequestResponseBinding(
                    new OID("1.3.6.1.2.1.1.1.0"),
                    new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"), new Integer32(999))
            )
    );

    SnmpClient client = clientFactory.create("127.0.0.1");

    Object actual = client.get(new OID("1.3.6.1.2.1.1.1.0"), "default");
    Object expected = new Integer32(999);

    assertThat(actual, is(expected));
  }

  @Test
  public void getBulkValues() throws Exception {
    server.pretendThatConfigurationWorkflowWillBe(
            new RequestResponseBinding(
                    new OID("1.3.6.1.2.1.1.1.0"),
                    new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"), new Integer32(777))
            ), new RequestResponseBinding(
                    new OID("1.3.6.1.2.1.1.1.1"),
                    new VariableBinding(new OID("1.3.6.1.2.1.1.1.1"), new Integer32(888))
            )
    );

    List<OID> oids = Lists.newArrayList(new OID("1.3.6.1.2.1.1.1.0"), new OID("1.3.6.1.2.1.1.1.1"));
    SnmpClient client = clientFactory.create("127.0.0.1");

    List<Variable> actual = client.getBulk(oids);
    List<Variable> expected = Lists.<Variable>newArrayList(new Integer32(777), new Integer32(888));

    assertThat(actual, is(expected));
  }
}