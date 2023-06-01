package com.vnco.fusiontech.product.web.rest.request;

<<<<<<< HEAD
=======
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.User;

import java.time.Instant;

>>>>>>> 49bcb8d (them chuc nang review san pham)
public record AddProductReviewRequest(int id, Integer userId, Integer productId, int rating, String comment) {

}
