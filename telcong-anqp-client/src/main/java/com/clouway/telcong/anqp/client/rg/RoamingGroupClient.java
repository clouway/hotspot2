package com.clouway.telcong.anqp.client.rg;

import java.util.List;

/**
 */
public interface RoamingGroupClient {
  /**
   * Create new roaming group
   *
   * @param group new roaming group which will be created
   */
  void create(NewRoamingGroup group);

  /**
   * Find all available roaming group
   *
   * @return list of {@link com.clouway.telcong.anqp.client.rg.RoamingGroup}
   */
  List<RoamingGroup> findAll();

  /**
   * Find roaming group by id
   *
   * @param id of the roaming group which will be retrieved
   * @return {@link com.clouway.telcong.anqp.client.rg.RoamingGroup}
   */
  RoamingGroup findByID(Object id);

  /**
   * Update roaming group
   *
   * @param group contains data which will be used to update roaming group
   */
  void update(Object id, RoamingGroupRequest group);

  /**
   * Assign operators to roaming group
   *
   * @param rgID id of the roaming group to which we will assigned operators
   * @param operIDs ids of operators which will be assigned to roaming group
   */
  void assignOperators(Object rgID, List<Object> operIDs);

  /**
   * Remove operators assigned to roaming group
   *
   * @param rgID id of the roaming group from which we will removed operators
   * @param operIDs ids of operators which well be remove from roaming group
   */
  void removeOperators(Object rgID, List<Object> operIDs);

  /**
   * Delete roaming group
   *
   * @param id of the roaming group which will be deleted
   */
  void delete(Object id);
}
