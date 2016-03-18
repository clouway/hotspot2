package com.clouway.telcong.anqp.client.operator;

import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.ap.AccessPoint;
import com.clouway.telcong.anqp.client.sp.ServiceProvider;

import java.util.List;

/**
 */
public interface OperatorClient {

  /**
   * Create new operator
   *
   * @param operator {@link com.clouway.telcong.anqp.client.operator.NewOperator}
   * @return id of the created operator
   */
  ID create(NewOperator operator);

  /**
   * Find all available operators
   *
   * @return list of {@link com.clouway.telcong.anqp.client.operator.Operator}
   */
  List<Operator> findAll();

  /**
   * Find operator by id
   *
   * @param id of the operator which we will retrieve
   * @return {@link com.clouway.telcong.anqp.client.operator.Operator}
   */
  Operator findByID(Object id);

  /**
   * Update operator
   *
   * @param operator contains data which will be used to update operator
   */
  void update(Object id, Operator operator);

  /**
   * Add APs to operator
   *
   * @param operID id if the operator to which we will add APs
   * @param apIDs  ids of APs which will be added
   */
  void assignAccessPoints(Object operID, List<Object> apIDs);

  /**
   * Find all APs which are assigned to operator
   *
   * @param id of the operator for which we will retrieve APs
   * @return list of {@link com.clouway.telcong.anqp.client.ap.AccessPoint}
   */
  List<AccessPoint> findAccessPoints(Object id);

  /**
   * Assign service providers to operator
   *
   * @param operID id if the operator to which we will add service providers
   * @param spIDs  ids of service provider which weill be assigned to operator
   */
  void assignServiceProviders(Object operID, List<Object> spIDs);

  /**
   * Remove service provider from operator
   *
   * @param operID id of the operator from which we will remove service providers
   * @param spIDs  ids of service providers which will be removed from operator
   */
  void removeServiceProviders(Object operID, List<Object> spIDs);

  /**
   * Find all service providers assigned to operator
   *
   * @param id of the operator for which we will find service providers
   * @return list of {@link com.clouway.telcong.anqp.client.sp.ServiceProvider}
   */
  List<ServiceProvider> findServiceProviders(Object id);

  /**
   * Activate operator
   *
   * @param id of the operator which we will activate
   */
  void activate(Object id);

  /**
   * Deactivate operator
   *
   * @param id of the operator which we will deactivate
   */
  void deactivate(Object id);

  /**
   * Update emergency number
   *
   * @param id        of the operator to which we will update emergency number
   * @param emergency contains new emergency number
   */
  void updateEmergencyNumber(Object id, NewEmergencyNumber emergency);

  /**
   * Delete operator
   *
   * @param id of the operator which will be deleted
   */
  void delete(Object id);
}
