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
  void update(RoamingGroupRequest group);

  /**
   * Implementation of this method add operators to roaming group.
   *
   * @param roamingGroupID is roaming group to which we will add operators.
   * @param operatorIDs is ids of operators which we will add to roaming group.
   */
  void assignOperators(ID roamingGroupID, List<ID> operatorIDs);

  /**
   * Implementation of this method remove operators from roaming group.
   *
   * @param roamingGroupID id of the roaming group from which we will remove operators
   * @param operatorIDs contains ids of operators which will be removed from roaming group
   */
  void removeOperators(ID roamingGroupID, List<ID> operatorIDs);

  /**
   * Delete roaming group
   *
   * @param id if the deleted roaming group
   */
  void delete(ID id);

  /**
   * Find roaming group by id.
   *
   * @param id of the roaming group for which we will search.
   *
   * @return {@link com.clouway.anqp.RoamingGroup}
   */
  Optional<RoamingGroup> findById(ID id);

  /**
   * Find all roaming groups
   *
   * @return list with {@link com.clouway.anqp.RoamingGroup}
   */
  List<RoamingGroup> findAll();
}
