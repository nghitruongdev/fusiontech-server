package com.vnco.fusiontech.product.web.rest.request;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.common.constraint.NullOrNotBlank;
import com.vnco.fusiontech.common.constraint.NullOrNotEmpty;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Specification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record VariantRequest(
        @NullOrNotBlank(groups = OnUpdate.class) String sku,
        @NotNull(groups = OnCreate.class) @Positive Double price,
        List<String> images,
        @NotEmpty(groups = OnCreate.class) @NullOrNotEmpty(groups = OnUpdate.class)
        List<@Valid Specification> specifications,
        List<@Valid VariantAttributeRequest> attributes,
        @NotNull(groups = OnCreate.class) @JsonIncludeProperties("id") Product product,
        Boolean active
        ) {

    public VariantRequest {
        if (sku != null)
            sku = sku.trim();
    }
    public interface OnCreate {

    }

    public interface OnUpdate {

    }
}

@Deprecated
record VariantAttributeRequest(
        @NotBlank String name,
        @NotBlank String value) {
    public VariantAttributeRequest {
        if (name != null)
            name = name.trim();
        if (value != null)
            value = value.trim();
    }

}
