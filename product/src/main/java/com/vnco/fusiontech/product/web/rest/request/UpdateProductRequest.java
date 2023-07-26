package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.common.entity.FirebaseImage;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

public record UpdateProductRequest(
                String name,
                String slug,
                Category category,
                Brand brand,
                FirebaseImage thumbnail,
                String summary,
                String description,
                List<String> features
//                List<ProductAttributeRequest> attributes,
//                List<S> specifications
) {

}
