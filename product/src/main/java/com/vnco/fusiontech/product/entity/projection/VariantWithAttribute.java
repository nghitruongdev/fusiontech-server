package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantAttribute;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection (name = Variant.PROJECTION.WITH_ATTRIBUTE, types = Variant.class)
public interface VariantWithAttribute {
        Long getId();
        String getImage();
        double getPrice();
        List<VariantAttribute> getAttributes();
}
