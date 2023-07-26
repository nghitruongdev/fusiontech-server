package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.product.web.rest.request.VariantRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RepositoryRestController
@RequiredArgsConstructor
public class VariantRestController {

  private final ProductVariantService variantService;

  @PostMapping("/variants")
  @Validated(VariantRequest.OnCreate.class)
  public ResponseEntity<?> createVariant(@RequestBody @Valid VariantRequest request) {
    variantService.createVariant(request);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/variants/{id}")
  @Validated(VariantRequest.OnUpdate.class)
  public ResponseEntity<?> partialUpdateVariant(@PathVariable Long id,
                                                @RequestBody @Valid VariantRequest request) {
    variantService.updateVariant(id, request);
    return ResponseEntity.ok().build();
  }
}
