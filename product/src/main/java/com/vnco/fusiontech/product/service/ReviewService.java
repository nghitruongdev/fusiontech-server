package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews();
    Review getReviewById(Long id);
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(Long id);
    List<Review> findAllByProductId(Long productId);




}
