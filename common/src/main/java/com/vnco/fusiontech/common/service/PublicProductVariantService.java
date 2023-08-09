package com.vnco.fusiontech.common.service;

import com.vnco.fusiontech.common.web.response.VariantWithProductInfoDTO;

import java.util.List;

public interface PublicProductVariantService {
    
    List<VariantWithProductInfoDTO> getVariantOrProductImages(Long variantId);
}
