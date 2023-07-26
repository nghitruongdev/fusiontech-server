package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Specification;

import java.util.List;

public record ListSpecificationRequest(String name, List<Specification> values) {
}
