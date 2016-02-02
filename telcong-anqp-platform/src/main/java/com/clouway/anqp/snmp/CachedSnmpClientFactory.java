package com.clouway.anqp.snmp;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates different kind of SimpleSnmpClient objects.
 */
final class CachedSnmpClientFactory implements SnmpClientFactory {
  private final Map<String, SnmpClient> hostToSnmpUtility = new ConcurrentHashMap<>();
  private final Integer agentPort;

  public CachedSnmpClientFactory(Integer agentPort) {
    this.agentPort = agentPort;
  }

  @Override
  public SnmpClient create(String sourceIpAddress) {
    SnmpClient client;

    if (hostToSnmpUtility.containsKey(sourceIpAddress)) {

      client = hostToSnmpUtility.get(sourceIpAddress);

    } else {

      try {

        client = new SimpleSnmpClient(sourceIpAddress, new Community("private", "public"), agentPort);
        client.start();

      } catch (IOException e) {
        throw new IllegalStateException("SNMP Client cannot be started.");
      }

      hostToSnmpUtility.put(sourceIpAddress, client);
    }

    return client;
  }
}