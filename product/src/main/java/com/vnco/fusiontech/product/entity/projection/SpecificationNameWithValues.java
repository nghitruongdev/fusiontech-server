package com.vnco.fusiontech.product.entity.projection;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
@Deprecated
public record SpecificationNameWithValues(String name, List<String> values) {

  public SpecificationNameWithValues(String name, Object values) {
    this(name, Arrays.asList(String.valueOf(values).split(",")));
  }
}
