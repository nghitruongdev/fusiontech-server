package com.vnco.fusiontech.security.web.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.vnco.fusiontech.common.web.request.RegisterUserWithEmailRequest;
import com.vnco.fusiontech.common.web.request.UpdateUserRequest;
import com.vnco.fusiontech.security.service.AccountService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthRestController {

    private final AccountService accountService;

    // TODO: test register user custom token = null, cần fix bug
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterUserWithEmailRequest request) {
        String returnToken = accountService.registerUserWithEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", returnToken));
    }

    @PostMapping("/register/google")
    public ResponseEntity<?> googleRegister(
            @RequestParam(name = "firebaseId", required = true) @NotBlank String firebaseId)
            throws FirebaseAuthException {
        var returnToken = accountService.registerWithGoogle(firebaseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", returnToken));
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request,
            @RequestParam(name = "userId", required = true) Long userId) {
        accountService.updateUser(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{firebaseId}")
    public ResponseEntity<?> deleteUser(@PathVariable("firebaseId") String firebaseId) throws FirebaseAuthException {
        throw new UnsupportedOperationException();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAdminData() {
        log.info("you're an admin");
        log.info("info: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok().build();
    }
}
