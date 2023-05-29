package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.repository.ReviewRepository;
import com.vnco.fusiontech.product.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Override
    public Review getReviewById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getAllReviewByProduct(String keyword) {
        List<Review> reviews = reviewRepository.getAllReviewByProduct(keyword);
        return reviews;

    }


}
