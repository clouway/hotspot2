package com.clouway.anqp;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Implementation of this interface contains CRUD operation related with operator profiles.
 */
public interface OperatorRepository {
  /**
   * Create new operator.
   * @param operator contains information for new operator.
   *
   * @return object id
   */
  Object create(NewOperator operator);

  /**
   * Update operator
   *
   * @param operator contains new data with which wi will update operator
   */
  void update(Operator operator);

  /**
   * Setting of emergency call number information for an operator
   *
   * @param newNumber is the new emergency number
   */
  void updateEmergencyNumber(NewEmergencyNumber newNumber);

  /**
   * Delete operator
   *
   * @param id of the operator which wi will delete
   */
  void delete(Object id);

  /**
   * Find all operators
   *
   * @return list with {@link com.clouway.anqp.Operator}
   */
  List<Operator> findAll();

  /**
   * Find operator by id
   * @param id by which we will search
   *
   * @return {@link com.clouway.anqp.Operator}
   */
  Optional<Operator> findById(Object id);
}
