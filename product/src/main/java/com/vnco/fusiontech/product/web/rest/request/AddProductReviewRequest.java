package com.vnco.fusiontech.product.web.rest.request;

import java.util.UUID;

public record AddProductReviewRequest(Long id, UUID userId, Long productId, short rating, String comment) {

}
