package com.clouway.telcong.anqp.client.apcontroller;

import java.util.List;

/**
 * This interface make CRUD operation over access point controller, as create, find, update and delete.
 */
public interface ApControllerClient {

  /**
   * Create new access point controller
   *
   * @param request contains information for new AP controller
   */
  void create(NewApController request);

  /**
   * Find all AP controlelrs

   * @return list with {@link com.clouway.telcong.anqp.client.apcontroller.ApController}
   */
  List<ApController> findAll();

  /**
   * Find one controller by id

   * @param id of the Ap controller which will be retrieved
   *
   * @return {@link com.clouway.telcong.anqp.client.apcontroller.ApController}
   */
  ApController findByID(Object id);

  /**
   * Update AP controller

   * @param id of the AP controller which will be updated
   *
   * @param request contains new data for AP controller
   */
  void update(Object id, ApController request);

  /**
   * Delete AP controller
   *
   * @param id of the AP controller which will be deleted
   */
  void delete(Object id);
}
