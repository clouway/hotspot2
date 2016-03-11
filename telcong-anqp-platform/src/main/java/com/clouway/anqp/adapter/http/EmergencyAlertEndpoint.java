package com.clouway.anqp.adapter.http;

import com.clouway.anqp.CapAlert;
import com.clouway.anqp.CapArea;
import com.clouway.anqp.CapInfo;
import com.clouway.anqp.CapMessage;
import com.clouway.anqp.CapResource;
import com.clouway.anqp.EmergencyAlertRepository;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import java.util.List;

@Service
@At("/r/emergency-alerts")
public class EmergencyAlertEndpoint {
  private final EmergencyAlertRepository repository;

  @Inject
  public EmergencyAlertEndpoint(EmergencyAlertRepository repository) {
    this.repository = repository;
  }

  @Get
  public Reply<?> fetchAll() {
    List<CapMessage> messages = repository.findAll();

    List<CapMessageDTO> dtos = adapt(messages);

    return Reply.with(dtos).as(Json.class).ok();
  }

  private List<CapMessageDTO> adapt(List<CapMessage> messages) {
    List<CapMessageDTO> dtos = Lists.newArrayList();

    for (CapMessage message : messages) {
      CapAlert alert = message.alert;

      List<CapInfoDTO> infos = adaptInfo(alert.infos);

      CapAlertDTO alerts = new CapAlertDTO(infos);

      dtos.add(new CapMessageDTO(message.id.value, alerts));
    }

    return dtos;
  }

  private List<CapInfoDTO> adaptInfo(List<CapInfo> infos) {
    List<CapInfoDTO> dtos = Lists.newArrayList();

    for (CapInfo info : infos) {
      List<String> resources = adaptResources(info.resources);

      List<String> areas = adaptAreas(info.areas);

      dtos.add(new CapInfoDTO(resources, areas));
    }

    return dtos;
  }

  private List<String> adaptResources(List<CapResource> resources) {
    List<String> dtos = Lists.newArrayList();

    for (CapResource resource : resources) {
      dtos.add(resource.content);
    }

    return dtos;
  }

  private List<String> adaptAreas(List<CapArea> areas) {
    List<String> dtos = Lists.newArrayList();

    for (CapArea area : areas) {
      dtos.add(area.content);
    }

    return dtos;
  }
}
