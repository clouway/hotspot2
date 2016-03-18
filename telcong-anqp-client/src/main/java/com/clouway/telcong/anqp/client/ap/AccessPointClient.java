package com.clouway.telcong.anqp.client.ap;

import com.clouway.telcong.anqp.client.ID;
import com.clouway.telcong.anqp.client.capability.Capability;

import java.util.List;

/**
 */
public interface AccessPointClient {

  /**
   * Create new AP
   *
   * @param ap which we will create
   * @return id of the created access point
   */
  ID create(NewAccessPoint ap);

  /**
   * Find all APs
   *
   * @return list of {@link com.clouway.telcong.anqp.client.ap.AccessPoint}
   */
  List<AccessPoint> findAll();

  /**
   * Find AP by its identifier
   *
   * @param id of AP which we will retrieve
   * @return {@link com.clouway.telcong.anqp.client.ap.AccessPoint}
   */
  AccessPoint findByID(Object id);

  /**
   * Retrieve query list for AP
   *
   * @param id of the AP
   * @return {@link com.clouway.telcong.anqp.client.ap.QueryList}
   */
  QueryList findQueryList(Object id);

  /**
   * Retrieve capability list of AP
   *
   * @param id of the AP
   * @return list of {@link com.clouway.telcong.anqp.client.capability.Capability}
   */
  List<Capability> findCapabilityList(Object id);

  /**
   * Retrieve emergency alert URI for AP
   *
   * @param id of the AP
   * @return emergency URI
   */
  String fetchEmergencyAlertURI(Object id);

  /**
   * Retrieve location URI for AP
   *
   * @param id of the AP
   * @return location URI
   */
  String fetchLocationURI(Object id);

  /**
   * Retrieve location of AP
   *
   * @param id of the AP
   * @return {@link com.clouway.telcong.anqp.client.ap.ApLocation}
   */
  ApLocation fetchLocation(Object id);

  /**
   * Update AP
   *
   * @param id of the AP which we will update
   * @param ap contains data which we will use to update AP
   */
  void update(Object id, AccessPointRequest ap);

  /**
   * Delete AP
   *
   * @param id of the AP which we will delete
   */
  void delete(Object id);
}
