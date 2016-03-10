package com.clouway.anqp.adapter.http;

import com.google.common.collect.ImmutableList;
import org.apache.bval.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 */
class NaiDTO {
  @NotEmpty
  @NotNull
  final String name;
  final String encoding;
  @Valid
  @Size(max = 4)
  final List<EapDTO> eaps;

  NaiDTO(String name, String encoding, List<EapDTO> eaps) {
    this.name = name;
    this.encoding = encoding;
    this.eaps = ImmutableList.copyOf(eaps);
  }
}
