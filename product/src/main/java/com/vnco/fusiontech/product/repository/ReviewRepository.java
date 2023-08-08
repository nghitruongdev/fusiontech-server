package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = true)
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByProductId(@Param("productId") Long productId);


}
