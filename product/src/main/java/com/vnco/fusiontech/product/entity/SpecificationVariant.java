//package com.vnco.fusiontech.product.entity;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@Table (name = "variant_specification")
//public class SpecificationVariant implements Serializable {
//    @Id
//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn (name = "variant_id")
//    private Variant variant;
//
//    @Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "specification_id")
//    private Specification specification;
//
//    // Constructors, getters, and setters (if needed)
//
//    // Important: Implement equals and hashCode based on the composite primary key
//    // to ensure correct behavior in collections like Set, List, etc.
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof SpecificationVariant)) return false;
//        SpecificationVariant that = (SpecificationVariant) o;
//        return Objects.equals(variant, that.variant) &&
//               Objects.equals(specification, that.specification);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(variant, specification);
//    }
//}
