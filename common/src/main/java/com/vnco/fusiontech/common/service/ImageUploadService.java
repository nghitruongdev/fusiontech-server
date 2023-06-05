package com.vnco.fusiontech.common.service;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Consumer;

public interface ImageUploadService {
    
    void uploadImages(String folder, Consumer<? super List<String>> callback,
                      @NonNull MultipartFile... files
    );
    
    void removeImages(String name);
}
