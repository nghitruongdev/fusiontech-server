package com.vnco.fusiontech.user;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@ComponentScan
@EntityScan(basePackages = "com.vnco.fusiontech.user.entity")
@EnableJpaRepositories(basePackages = "com.vnco.fusiontech.user.repository")
public class UserModuleConfiguration {

    @PostConstruct
    public void initialize() throws IOException {
        //Firebase config
        String SERVICE_ACCOUNT_URL = "D:\\Workspace\\VnCo\\fusiontech-server\\.local.fusiontech-vnco4-firebase-adminsdk-5oius-d1147d3d39.json";
        FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_URL);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("")
                .build();
        if (FirebaseApp.getApps().isEmpty())
            FirebaseApp.initializeApp(options);
    }

}
