package com.vnco.fusiontech.security.web.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import com.vnco.fusiontech.security.service.AccountService;
import com.vnco.fusiontech.security.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class SecurityRestController {

    final AccountService accountService;
    final FirebaseService firebaseService;

    public SecurityRestController(AccountService accountService, FirebaseService firebaseService) {
        this.accountService = accountService;
        this.firebaseService = firebaseService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterUser request) throws FirebaseAuthException {
        String customToken = accountService.userRegister(request);
        if (customToken != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", customToken);
            log.info("token: {}", customToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
    }

    @PostMapping("/google/register")
    public ResponseEntity<?> googleRegister(@RequestBody String token) throws FirebaseAuthException {
        log.info("google registration {}", token);
        accountService.googleRegister(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request,
                                        @PathVariable("id") String id) {
        accountService.updateUser(request, Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{firebaseId}")
    public ResponseEntity<?> deleteUser(@PathVariable("firebaseId") String firebaseId) throws FirebaseAuthException {
        accountService.deleteUser(firebaseId);
        log.info("Deleted {}", firebaseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAdminData() {
            log.info("you're an admin");
            log.info("info: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            return ResponseEntity.ok().build();
    }
}
