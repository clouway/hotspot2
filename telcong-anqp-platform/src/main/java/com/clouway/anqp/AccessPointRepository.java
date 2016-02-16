package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 */
public interface AccessPointRepository {
  Object create(NewAccessPoint ap);

  void update(AccessPoint ap);

  void delete(Object id);

  Optional<AccessPoint> findById(Object id);

  Optional<AccessPoint> findByIp(String ip);

  QueryList findQueryList(Object id);

  List<AccessPoint> findAll();
}