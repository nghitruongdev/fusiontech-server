package com.vnco.fusiontech.common.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class FileUtils {
    
    @SneakyThrows
    public static File convertToTempFile(@NonNull MultipartFile file) {
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String ext      = originalName.substring(originalName.lastIndexOf("."));
        Path   tempPath = Files.createTempFile(UUID.randomUUID().toString(), ext);
        Files.copy(file.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);
        var tempFile = tempPath.toFile();
        
        tempFile.deleteOnExit();
        return tempFile;
    }
    
    public static List<File> convertToTempFile(@NonNull MultipartFile... files) {
        return Arrays.stream(files).map(FileUtils::convertToTempFile).toList();
    }
    
    @SneakyThrows
    public static String base64Encoder(File file) {
        return Base64.getUrlEncoder().encodeToString(Files.readAllBytes(file.toPath()));
        //        return Base64.getUrlEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
    }
}
