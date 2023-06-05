package com.vnco.fusiontech.product.web.rest.request;

public record AddProductReviewRequest(int id, Integer userId, Integer productId, int rating, String comment) {

}
