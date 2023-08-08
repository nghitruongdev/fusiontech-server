package com.vnco.fusiontech.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.net.URLDecoder;
@Slf4j
public class FirebaseUtils {
    public static String convertToE164Format(@Nullable String phoneNumber) {
        if(phoneNumber == null) return null;
        if(phoneNumber.startsWith("+84")) return phoneNumber;
       
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", ""); // remove non digits
        if (cleanNumber.startsWith("0")) {
            cleanNumber = "84" + cleanNumber.substring(1); // replace leading 0 with 84
        }
        return "+" + cleanNumber; // prepend +
    }
    
    public static String composeFullName(String firstName, String lastName) {
        String first = (firstName != null) ? firstName.trim() : "";
        String last  = (lastName != null) ? lastName.trim() : "";
        return (first + " " + last).trim();
    }
    
    public static String composeFullName(String firstName, String lastName, String userFirstName, String userLastName) {
        String first = firstName != null && !firstName.isBlank() ? firstName.trim() : userFirstName != null?
                                                                                      userFirstName.trim(): "";
        String last  = lastName !=null && !lastName.isBlank() ? lastName.trim(): userLastName!=null?
                                                                                 userLastName.trim(): "";
        return (first + " " + last).trim();
    }
    
    public static String getImagePath(String url) {
        int lastSlashIndex = url.lastIndexOf("/images");
        int questionMarkIndex = url.indexOf('?');
        
        if (lastSlashIndex >= 0 && questionMarkIndex >= 0) {
            String encodedName = url.substring(lastSlashIndex + 1, questionMarkIndex);
            try {
                // Decode the URL component to replace %2F with forward slash
                return URLDecoder.decode(encodedName, "UTF-8");
            } catch (Exception e) {
            log.error("UnsupportedEncoding: {}", e.getMessage());
            }
        }
        
        return "";
    }
    
}
