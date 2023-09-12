package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Specification;

import java.util.Collection;

public interface SpecificationService {
    void persistSpecifications(Collection<Specification> specifications);
}
