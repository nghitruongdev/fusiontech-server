package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.entity.Variant;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = Variant.PROJECTION.WITH_SPECS, types = Variant.class)
public interface VariantWithSpecs extends VariantBasic {

        int getAvailableQuantity();

        List<Specification> getSpecifications();
}
