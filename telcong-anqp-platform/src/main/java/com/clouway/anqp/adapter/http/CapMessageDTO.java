package com.clouway.anqp.adapter.http;

class CapMessageDTO {
  final Object id;
  final CapAlertDTO alert;

  CapMessageDTO(Object id, CapAlertDTO alert) {
    this.id = id;
    this.alert = alert;
  }
}
