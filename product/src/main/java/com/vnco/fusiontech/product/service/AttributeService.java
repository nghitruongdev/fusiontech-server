package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.entity.Product;

import java.util.List;

public interface AttributeService {
    List<Attribute> getAllAttributes();
    Attribute getAttributeById(int id);
    Attribute createAttribute(Attribute attribute);
    Attribute updateAttribute(Attribute attribute);
    void deleteAttribute(int id);
}
