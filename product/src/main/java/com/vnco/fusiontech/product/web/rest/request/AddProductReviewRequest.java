package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.User;

import java.time.Instant;

public record AddProductReviewRequest(int id, Integer userId, Integer productId, int rating, String comment) {

}
