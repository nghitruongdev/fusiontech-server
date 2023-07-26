package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


public interface SpecificationRepository extends JpaRepository<Specification, Long> {

    @RestResource(path = "findByName")
    List<Specification> findDistinctByNameLikeIgnoreCase(@Param ("name")String name);
    
    @Query("SELECT DISTINCT s.name FROM Specification s")
    @RestResource(exported = false)
    List<String> findAllDistinctName();
    
    // @Query (
    // """
    // SELECT DISTINCT new
    // com.vnco.fusiontech.product.entity.projection.ProductAttributeDTO(va.name,
    // va.values)
    // FROM VariantAttribute va JOIN Variant v ON va.variant.id = v.id
    // WHERE v.product.id =:productId
    // """
    // )
    // @RestResource (path = "attributes")
    // List<ProductAttributeDTO> findDistinctNameAndAttributes(@Param ("productId")
    // Long productId);
    
    //        @Query("SELECT DISTINCT va.name FROM VariantAttribute va WHERE va.variant.product.id=:productId")
    //        @RestResource(exported = false)
    //        List<String> findDistinctAttributeNames(@Param("productId") Long productId);
    
    //        @Query("SELECT DISTINCT va.value FROM VariantAttribute va WHERE va.name in :names")
    //        @RestResource(exported = false)
    //        List<String> findAttributesByName(@Param("names") List<String> names);
    
    
    //        @Query("""
    //                        SELECT DISTINCT va.name, GROUP_CONCAT(DISTINCT va.value) FROM VariantAttribute va
    //                         WHERE va.name IN (SELECT sva.name FROM VariantAttribute sva WHERE sva.variant.product.id=:productId)
    //                         GROUP BY va.name
    //                        """)
    //        List<String[]> findDistinctNamesWithAllAttributes(@Param("productId") Long productId);
    
    
    // @Query (
    // """
    // SELECT DISTINCT new
    // com.vnco.fusiontech.product.entity.projection.ProductAttributeDTO(va.name,
    // va.values)
    // FROM VariantAttribute va JOIN Variant v ON va.variant.id = v.id
    // WHERE v.product.id =:productId
    // """
    // )
    // @RestResource (path = "attributes")
    // List<ProductAttributeDTO> findDistinctNameAndAttributes(@Param ("productId")
    // Long productId);
    
    //        @Query("SELECT DISTINCT va.name FROM VariantAttribute va WHERE va.variant.product.id=:productId")
    //        @RestResource(exported = false)
    //        List<String> findDistinctAttributeNames(@Param("productId") Long productId);
    
    //        @Query("SELECT DISTINCT va.value FROM VariantAttribute va WHERE va.name in :names")
    //        @RestResource(exported = false)
    //        List<String> findAttributesByName(@Param("names") List<String> names);
    
    // @Query (
    // """
    // SELECT DISTINCT va.name, GROUP_CONCAT(DISTINCT va.value) FROM
    // VariantAttribute va
    // WHERE va.variant.product.id=:productId
    // GROUP BY va.name
    // """
    // )
    //        @Query("""
    //                        SELECT DISTINCT va.name, GROUP_CONCAT(DISTINCT va.value) FROM VariantAttribute va
    //                         WHERE va.name IN (SELECT sva.name FROM VariantAttribute sva WHERE sva.variant.product.id=:productId)
    //                         GROUP BY va.name
    //                        """)
    //        List<String[]> findDistinctNamesWithAllAttributes(@Param("productId") Long productId);
    
//            @Query("""
//                            SELECT DISTINCT new
//                                            com.vnco.fusiontech.product.entity.projection.SpecificationNameWithValues(spec.name,
//                            GROUP_CONCAT(DISTINCT spec)) FROM
//                            Specification spec
//                            WHERE (SELECT v FROM Variant v WHERE v.product.id =:productId )
//                            GROUP BY spec.name
//                            """)
//            List<SpecificationNameWithValues> findDistinctNamesWithProductAttributes(@Param("productId") Long productId);
    
}
