package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 */
public interface RoamingGroupRepository {
  /**
   * Create new roaming group
   *
   * @param group which be created
   *
   * @return id if the new roaming group
   */
  Object create(NewRoamingGroup group);

  /**
   * Update roaming group
   *
   * @param group contains data with we will update group.
   */
  void update(RoamingGroup group);

  /**
   * Delete roaming group
   *
   * @param id if the deleted roaming group
   */
  void delete(Object id);

  /**
   * Find roaming group by id.
   *
   * @param id of the roaming group for which we will search.
   *
   * @return {@link com.clouway.anqp.RoamingGroup}
   */
  Optional<RoamingGroup> findById(Object id);

  /**
   * Find all roaming groups
   *
   * @return list with {@link com.clouway.anqp.RoamingGroup}
   */
  List<RoamingGroup> findAll();
}
