package com.vnco.fusiontech.product.service.impl;


import com.vnco.fusiontech.common.exception.NotAcceptedRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.repository.VariantInventoryDetailRepository;
import com.vnco.fusiontech.product.repository.VariantInventoryRepository;
import com.vnco.fusiontech.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final VariantInventoryRepository       repository;
    private final VariantInventoryDetailRepository detailRepository;
    private final ProductVariantRepository         variantRepository;
    
    @Override
    public Long createInventory(VariantInventory inventory) {
        return repository.save(inventory).getId();
    }
    
    @Override
    public void updateItem(Long itemId, VariantInventoryDetail updateDetail) {
        var detail            = detailRepository.findById(itemId).orElseThrow(RecordNotFoundException::new);
        var availableQuantity = variantRepository.getAvailableQuantity(detail.getVariantId());
        var exception = new NotAcceptedRequestException("Không thể cập nhật - số lượng khả dụng không đủ");
        if (Objects.equals(detail.getVariantId(), updateDetail.getVariant().getId())) {
            if (detail.getQuantity() > updateDetail.getQuantity()) {
                var difference = detail.getQuantity() - updateDetail.getQuantity();
                if (availableQuantity - difference < 0)
                    throw exception;
            }
            detail.setQuantity(updateDetail.getQuantity());
            return;
        }
        
        if (availableQuantity - detail.getQuantity() < 0) {
            throw exception;
        }
        
        detail.setVariantId(updateDetail.getVariant().getId());
        detail.setQuantity(updateDetail.getQuantity());
    }
    
    @Override
    public void deleteItem(Long itemId) {
        var detail            = detailRepository.findById(itemId).orElseThrow(RecordNotFoundException::new);
        var availableQuantity = variantRepository.getAvailableQuantity(detail.getVariantId());
        var exception = new NotAcceptedRequestException("Không thể xoá - số lượng khả dụng không đủ");
        if (availableQuantity - detail.getQuantity() < 0) {
            throw exception;
        }
        repository.deleteById(itemId);
    }
    
}
