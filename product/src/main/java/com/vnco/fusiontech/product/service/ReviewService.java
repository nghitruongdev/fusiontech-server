package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductVariant;
import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.User;
import com.vnco.fusiontech.product.web.rest.request.AddProductReviewRequest;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface ReviewService {

    Review getReviewById(int id);
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(int id);
    List<Review> findAllByProductId(Integer productId);



}
