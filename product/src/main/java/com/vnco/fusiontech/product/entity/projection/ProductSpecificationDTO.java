package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Specification;

import java.util.List;

public record ProductSpecificationDTO(String name, List<Specification> values) {
}
