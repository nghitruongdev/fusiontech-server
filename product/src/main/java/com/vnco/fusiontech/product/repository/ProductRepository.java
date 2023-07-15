package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
    @RestResource(path = "byKeyWord", rel = "keyword")
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword);
    
    @RestResource(path = "favorites", rel = "favorites")
    List<Product> findAllByFavorites_Id(@Param("uid") UUID userId);
    
    
}
