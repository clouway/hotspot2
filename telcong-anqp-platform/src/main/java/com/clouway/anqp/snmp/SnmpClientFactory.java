package com.clouway.anqp.snmp;

/**
 * Creates different kind of SimpleSnmpClient objects.
 */
public interface SnmpClientFactory {
  SnmpClient create(String sourceIpAddress, Community community);
}