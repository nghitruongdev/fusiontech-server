package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.common.utils.SecurityUtils;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.service.UserService;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
@Validated
public class AuthRestController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Validated({UserRequest.OnRegister.class})
    public ResponseEntity<?> userRegister(@RequestBody @Valid UserRequest request) {
        String token = userService.registerWithEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    @PostMapping("/register/google")
    public ResponseEntity<?> googleRegister(
            @RequestParam(name = "firebaseId") @NotBlank String firebaseId) {
        var token = userService.registerWithGoogle(firebaseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    @PatchMapping("/update-profile")
    @Validated(UserRequest.OnUpdate.class)
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequest request,
            @RequestParam(name = "userId") Long userId) {
        userService.updateUser(request, userId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> getCurrentUser() {
     var user =   SecurityUtils.getCurrentUserLogin().get();
        return ResponseEntity.ok(Map.of("token", user));
    }
    
    @GetMapping ("/verify-email")
    public ResponseEntity<?> getVerifyLink(@RequestBody String email) {
        var user = authService.verifyEmail(email);
        if (user) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
    
    @GetMapping("/test-verify")
    public ResponseEntity<?> vefiry(@RequestBody String email) {
        var user = authService.generateVerifyLink(email);
        return ResponseEntity.ok(user);
    }
    
}
