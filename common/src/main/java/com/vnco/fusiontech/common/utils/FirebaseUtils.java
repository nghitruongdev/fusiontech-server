package com.vnco.fusiontech.common.utils;

import org.springframework.lang.Nullable;

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
        String last = (lastName != null) ? lastName.trim() : "";
        return (first + " " + last).trim();
    }
}
