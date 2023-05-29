package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/attribute")
public class AttributeRestController {
    @Autowired
    AttributeService attributeService;

    @GetMapping
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @GetMapping("/{id}")
    public Attribute getAttributeById(@PathVariable int id) {
        return attributeService.getAttributeById(id);
    }

    @PostMapping
    public Attribute createAttribute(@RequestBody Attribute attribute) {
        return attributeService.createAttribute(attribute);
    }

    @PutMapping("/{id}")
    public Attribute updateAttribute(@PathVariable("id") Integer id,@RequestBody Attribute attribute) {
        return attributeService.updateAttribute(attribute);
    }

    @DeleteMapping("/{id}")
    public void deleteAttribute(@PathVariable int id) {
        attributeService.deleteAttribute(id);
    }
}
