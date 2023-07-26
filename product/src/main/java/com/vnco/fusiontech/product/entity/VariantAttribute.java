//package com.vnco.fusiontech.product.entity;
//
//import com.vnco.fusiontech.common.constant.DBConstant;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.Accessors;
//
//import java.io.Serializable;
//
//@Accessors(chain = true)
//@Builder
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Entity
//@Table(name = DBConstant.VARIANT_ATTRIBUTE_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = { "name",
//        "variant_id" }))
//@Deprecated
//public class VariantAttribute implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(name = "attribute_value")
//    private String value;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private Variant variant;
//
//}
