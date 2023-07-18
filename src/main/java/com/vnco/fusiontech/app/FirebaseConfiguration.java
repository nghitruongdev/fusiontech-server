package com.vnco.fusiontech.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfiguration {
    private final ResourceLoader loader;
    
    @PostConstruct
    public void initialize() throws IOException {
        final var          SERVICE_ACCOUNT_URL = "classpath:.local.fusiontech-vnco4-firebase-adminsdk-5oius" +
                                                 "-d1147d3d39" +
                                              ".json";
        var resource = loader.getResource(SERVICE_ACCOUNT_URL);
        
        FirebaseOptions options = FirebaseOptions.builder()
                                                 .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                                                 //                .setDatabaseUrl("")
                                                 .build();
        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }
    }
}
