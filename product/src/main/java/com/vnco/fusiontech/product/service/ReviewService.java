package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.ProductVariant;
import com.vnco.fusiontech.product.entity.Review;

import java.util.List;

public interface ReviewService {

    Review getReviewById(int id);
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(int id);
    List<Review> getAllReviewByProduct(String keyword);



}
