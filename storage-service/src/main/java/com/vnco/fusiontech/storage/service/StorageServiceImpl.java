package com.vnco.fusiontech.storage.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.annotations.NotNull;
import com.vnco.fusiontech.common.service.PublicStorageService;
import com.vnco.fusiontech.common.utils.FirebaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StorageServiceImpl implements PublicStorageService {
    private final Bucket bucket = StorageClient.getInstance().bucket();
    
    private final Queue<String> removeImageUrls = new ArrayDeque<>();
    
    @Override
    public void removeImages(@NotNull List<String> imageUrls) {
        removeImageUrls.addAll(imageUrls.stream()
                                        .map(FirebaseUtils::getImagePath).toList());
    }
    
    @Scheduled (timeUnit = TimeUnit.HOURS, fixedRate = 1L)
    private void removeImages() {
        log.debug("Remove images ran");
        log.warn("Images {}", removeImageUrls);
        if (removeImageUrls.isEmpty()) {
            return;
        }
        removeImageUrls.stream().peek(path -> log.debug("Removing image: {}", path))
                       .map(bucket::get).filter(Objects::nonNull)
                       .filter(Blob::exists)
                       .map(Blob::delete)
                       .forEach((item) -> log.debug(
                               "Successfully deleted {}", item));
        removeImageUrls.clear();
    }
}
