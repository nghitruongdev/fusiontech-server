package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.domain.Page;
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

    @RestResource(path = "byCategoryId", rel = "category")
    @Query("SELECT p FROM Product p WHERE p.category.id = :cid ")
    List<Product> searchByCategoryId(@Param("cid") Integer cid);

    @RestResource(path = "favorites", rel = "favorites")
    List<Product> findAllByFavorites_Id(@Param("uid") Long userId);

    @Query("""
                     SELECT COALESCE(SUM (get_sold_count(v.id)), 0)
                     FROM Variant v WHERE v.product.id=:productId
            """)
    @RestResource(path = "countProductSold", rel = "countProductSold")
    Long countProductNumberSold(@Param("productId") Long productId);

    @Query(
            """
                    SELECT COALESCE(SUM(get_available_quantity(v.id)), 0)
                     FROM Variant v WHERE v.product.id =:id
                    AND v.active = TRUE
                    """)
    @RestResource(path = "availableQuantityByProduct", rel = "availableQuantityByProduct")
    Long getAvailableQuantityByProduct(@Param("id") Long productId);

    @RestResource(path = "all", rel = "all")
    @Query("from Product WHERE active = true")
    List<Product> findAllWithoutPage();

    @RestResource(path = "active", rel = "active")
    @Query("from Product WHERE active = TRUE")
    List<Product> findActiveProduct(Pageable pageable);

    @RestResource(path = "many", rel = "many")
    List<Product> findAllByIdIn(@Param("ids") List<Long> ids);

    @Query("""
            SELECT p FROM Product p
            WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:name),'%') """)
    @RestResource(path = "findByNameLike", rel = "findByNameLike")
    List<Product> findByNameLikeIgnoreCase(@Param("name") String name);

    @RestResource(path = "find-by-name", rel = "findByName")
    Product findFirstByNameIgnoreCase(@Param("name") String name);

    @RestResource(path = "find-by-slug", rel = "findBySlug")
    Product findFirstBySlugIgnoreCase(@Param("slug") String slug);

    @Query("""
            SELECT DISTINCT p.status FROM Product p""")
    List<String> findAllProductStatus();

    @Query("""
            SELECT  v2.product FROM OrderItem oi1
            JOIN OrderItem oi2 ON oi1.order = oi2.order
            JOIN Variant v1 ON v1.id = oi1.variant.id
            JOIN Variant v2 ON v2.id = oi2.variant.id
            WHERE v1.product.id =:id AND v1.product.id <> v2.product.id
            GROUP BY v2.product
            ORDER BY COUNT(v2.product.id) DESC
            """)
    Slice<Product> findTopFrequentBoughtTogether(@Param("id") Long productId, Pageable pageable);

    @Query("""
                FROM Product p
                WHERE p.active = true
                AND p.discount > 0
                ORDER BY p.discount DESC
            """)
    @RestResource(path = "discount-products")
    Page<Product> getDiscountProducts(Pageable pageable);

    @Query("""
                    FROM Product p
                    WHERE p.active = true
                    ORDER BY p.id desc
            """)
    @RestResource(path = "latest-products")
    Page<Product> getLatestAddedProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status LIKE 'HOT' AND p.active = true")
    @RestResource(path = "hot-products")
    Page<Product> getHotProducts(Pageable pageable);

    @Query(
            """
                            SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END FROM VariantInventoryDetail i WHERE i.variant.product.id =:id
                    """)
    @RestResource(path = "has-import-inventory")
    Boolean hasImportInventory(@Param("id") Long id);

    // @Query (
    // """
    // SELECT new com.vnco.fusiontech.product.entity.projection.DynamicProductInfo(
    // p.discount,
    // p.status,
    // p.active,
    // get_product_available_quantity(p.id),
    // get_product_min_price(p.id),
    // get_product_max_price(p.id)
    // ) FROM Product p WHERE p.id =:id
    // """
    // )
    // DynamicProductInfo getDynamicProductInfo(@Param ("id") Long productId);

}
