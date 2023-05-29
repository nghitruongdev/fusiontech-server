package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    ReviewService reviewService;

    // lay review theo id
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    // them moi review
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }


    // xoa review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }

    // tim kiem review theo san pham
    @GetMapping("/search")
    public List<Review> getAllReviewByProduct(@RequestParam("keyword")String keyword){
        List<Review> reviews = reviewService.getAllReviewByProduct(keyword);
        return reviews;
    }
}
