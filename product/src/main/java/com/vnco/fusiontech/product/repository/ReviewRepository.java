package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    @Query("SELECT r FROM Review r WHERE 'r.product' LIKE %:keyword% ")
    List<Review> getAllReviewByProduct(@Param("keyword") String keyword);
}
