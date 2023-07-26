package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.projection.ReviewDTO;
import com.vnco.fusiontech.product.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    ReviewService reviewService;

    @GetMapping()
    public List<Review> getAll() {
        return reviewService.getAllReviews();
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable("id") Integer id,@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    // lay review theo id
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
    // xoa review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/search/findAllReviewsByProductId")
    public List<ReviewDTO> findAllByProductId(@RequestParam("pid") Long productId) {
        List<Review> reviews = reviewService.findAllByProductId(productId);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setComment(review.getComment());
            reviewDTO.setCreatedAt(review.getCreate_at());
            reviewDTO.setUser(review.getUser());

            reviewDTOs.add(reviewDTO);
        }

        return reviewDTOs;
    }


}
