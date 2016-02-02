package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 */
public interface ApControllerRepository {
  Object create(NewApController controller);

  void update(ApController controller);

  void delete(Object id);

  Optional<ApController> findById(Object id);

  Optional<ApController> findByIp(String ip);

  List<ApController> findAll();
}