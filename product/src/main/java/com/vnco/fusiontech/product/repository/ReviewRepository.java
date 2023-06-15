package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findReviewsByProductIdIs(Integer productId);
}
