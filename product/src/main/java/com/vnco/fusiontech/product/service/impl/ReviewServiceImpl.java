package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.User;
import com.vnco.fusiontech.product.repository.ReviewRepository;
import com.vnco.fusiontech.product.service.ReviewService;
import com.vnco.fusiontech.product.service.UserService;
import com.vnco.fusiontech.product.web.rest.request.AddProductReviewRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
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
    public List<Review> findAllByProductId(Integer productId) {
        return reviewRepository.findReviewsByProductIdIs(productId);
    }



}
