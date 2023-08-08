package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.repository.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RepositoryRestController
@RequiredArgsConstructor
public class SpecificationRestController
{
    private final SpecificationRepository repository;
    
    @GetMapping("/specifications/search/distinct-names")
    public ResponseEntity<?> getAllDistinctNames(){
        log.warn("Using repository directly inside controller");
        return ResponseEntity.ok(repository.findAllDistinctName());
    }
    
}
