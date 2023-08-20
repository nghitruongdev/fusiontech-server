package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @RestResource(path = "all", rel = "all")
    @Query("from Brand ")
    List<Brand> findAllWithoutPage();

    @RestResource(path = "find-by-name", rel = "findByName")
    Optional<Brand> findFirstByNameEqualsIgnoreCase(@Param("name") String name);

    @RestResource(path = "find-by-slug", rel = "findBySlug")
    Optional<Brand> findBySlug(@Param("slug") String slug);

    @RestResource(path = "byBrandId", rel = "byBrandId")
    Optional<Brand> searchBrandById(@Param("bid") Integer bid);
}
