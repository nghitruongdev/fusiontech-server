package com.vnco.fusiontech.product.event;

import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RequiredArgsConstructor
@RepositoryEventHandler
public class InventoryDetailHandler {

    @HandleBeforeSave
    public void handleBeforeSave(VariantInventoryDetail detail){
    }
    @HandleAfterSave
    public void handleAfterSave(VariantInventoryDetail detail){
    }
}
