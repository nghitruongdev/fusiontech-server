package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.repository.AttributeRepository;
import com.vnco.fusiontech.product.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    @Override
    public Attribute getAttributeById(int id) {
        return attributeRepository.findById(id).orElse(null);
    }

    @Override
    public Attribute createAttribute(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public Attribute updateAttribute(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public void deleteAttribute(int id) {
        attributeRepository.deleteById(id);
    }
}
