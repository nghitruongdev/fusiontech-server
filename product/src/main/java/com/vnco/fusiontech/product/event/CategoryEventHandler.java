package com.vnco.fusiontech.product.event;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@RepositoryEventHandler
@Component
public class CategoryEventHandler {
    private final CategoryRepository repository;
    
    @HandleBeforeCreate
    public void handleBeforeCreate(Category o) {
//        var cat = repository.findBySlug(o.getSlug());
//        if (cat.isPresent()) {
//            throw new DuplicateKeyException("The given slug already exists");
//        }
    }
    
    @HandleBeforeSave
    public void handleBeforeSave(Category o) {
//        var cat = repository.findBySlug(o.getSlug());
//        if (cat.isPresent() && !cat.get().equals(o)) {
//            throw new DuplicateKeyException("The new slug already exists");
//        }
    }
}
