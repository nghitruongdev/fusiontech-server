package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
        @RestResource(path = "byKeyWord", rel = "keyword")
        @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
        List<Product> searchByKeyword(@Param("keyword") String keyword);

        @RestResource(path = "favorites", rel = "favorites")
        List<Product> findAllByFavorites_Id(@Param("uid") Long userId);
        
        @Query (
                """
                         SELECT COALESCE(SUM (oi.quantity), 0) FROM OrderItem oi JOIN Order o ON oi.order = o
                         WHERE oi.variant.id IN (SELECT v.id FROM Variant v WHERE v.product.id =:productId) AND
                         o.status = 'DELIVERED_SUCCESS'
                """
        )
        @RestResource (path = "countProductSold", rel = "countProductSold")
        Long countProductNumberSold(@Param ("productId") Long productId);
        
        @Query (
                """
                SELECT COALESCE(SUM(get_available_quantity(v.id)), 0) FROM Variant v WHERE v.product.id =:id
                AND v.active = TRUE
                """
        )
        @RestResource(path = "availableQuantityByProduct", rel = "availableQuantityByProduct")
        Long getAvailableQuantityByProduct(@Param("id") Long productId);
        
        @RestResource (path = "all", rel = "all")
        @Query ("from Product ")
        List<Product> findAllWithoutPage();
        
        @RestResource (path = "many", rel = "many")
        List<Product> findAllByIdIn(@Param ("ids") List<Long> ids);
        
        @Query (
                """
                SELECT  v2.product FROM OrderItem oi1
                JOIN OrderItem oi2 ON oi1.order = oi2.order
                JOIN Variant v1 ON v1.id = oi1.variant.id
                JOIN Variant v2 ON v2.id = oi2.variant.id
                WHERE v1.product.id =:id AND v1.product.id <> v2.product.id
                GROUP BY v2.product
                ORDER BY COUNT(v2.product.id) DESC
                """
        )
        Slice<Product> findTopFrequentBoughtTogether(@Param ("id") Long productId, Pageable pageable);
}
