//package com.vnco.fusiontech.product.service.impl;
//
//import com.vnco.fusiontech.product.entity.VariantAttribute;
//import com.vnco.fusiontech.product.repository.ProductAttributeRepository;
//import com.vnco.fusiontech.product.service.ProductAttributeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Deprecated
//public class ProductAttributeServiceImpl implements ProductAttributeService {
//    @Autowired
//    private ProductAttributeRepository productAttributeRepository;
//
//    @Override
//    public List<VariantAttribute> getAllProductAttributes() {
//        return productAttributeRepository.findAll();
//    }
//
//    @Override
//    public VariantAttribute getProductAttributeById(int id) {
//        return productAttributeRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public VariantAttribute createProductAttribute(VariantAttribute productAttribute) {
//        return productAttributeRepository.save(productAttribute);
//    }
//
//    @Override
//    public VariantAttribute updateProductAttribute(VariantAttribute productAttribute) {
//        return productAttributeRepository.save(productAttribute);
//    }
//
//    @Override
//    public void deleteProductAttribute(int id) {
//        productAttributeRepository.deleteById(id);
//    }
//}
