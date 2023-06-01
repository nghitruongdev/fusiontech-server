package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.User;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ReviewService;
import com.vnco.fusiontech.product.service.UserService;
import com.vnco.fusiontech.product.web.rest.request.AddProductReviewRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    ReviewService reviewService;



    @PutMapping("/{id}")
    public Review updateReview(@PathVariable("id") Integer id,@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
    // xoa review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/search/findAllByProductId")
    public List<Review> findAllByProductId(@RequestParam("pid") Integer productId){
        List<Review> reviews = reviewService.findAllByProductId(productId);
        return reviews;
    }

}
