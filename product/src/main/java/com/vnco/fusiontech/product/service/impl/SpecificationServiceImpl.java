package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.repository.SpecificationRepository;
import com.vnco.fusiontech.product.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class SpecificationServiceImpl implements SpecificationService {
    private final SpecificationRepository repository;
    
    @Override
    public void persistSpecifications(Collection<Specification> specifications) {
        var unsaved = specifications.stream().filter(item-> item.getId() == null).toList();
        repository.saveAll(unsaved);
    }
}
