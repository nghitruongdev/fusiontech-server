package com.vnco.fusiontech.security.web.rest;

import com.google.firebase.auth.*;
import com.vnco.fusiontech.common.constant.FirebaseConstant;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import com.vnco.fusiontech.security.service.AccountService;
import com.vnco.fusiontech.security.service.FirebaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class AccountRestController {

    final AccountService accountService;
    final FirebaseService firebaseService;

    public AccountRestController(AccountService accountService, FirebaseService firebaseService) {
        this.accountService = accountService;
        this.firebaseService = firebaseService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request) {
        try {
            request.getAttribute(FirebaseConstant.ATTRIBUTE);
            log.info("Information {}", request);
            log.info("registration completed !");
            FirebaseToken decodedToken = firebaseService.decodedToken(request);
            String uid = decodedToken.getUid();
            accountService.register(decodedToken);
            log.info("user id {}", uid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("registration failed !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest requestToken,
                                        HttpServletRequest request) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseService.decodedToken(request);
        accountService.updateUser(requestToken, decodedToken);
//        UserRecord userRecord = FirebaseAuth.getInstance().getUser(decodedToken.getUid());
//        UserRecord.UpdateRequest firebaseUser = userRecord.updateRequest();
//        requestToken.name().ifPresent(firebaseUser::setDisplayName);
//        requestToken.email().ifPresent(firebaseUser::setEmail);
//        requestToken.phoneNumber().ifPresent(firebaseUser::setPhoneNumber);
//        requestToken.photoUrl().ifPresent(firebaseUser::setPhotoUrl);

//        UserRecord updateFirebaseUser = FirebaseAuth.getInstance().updateUser(firebaseUser);
        return ResponseEntity.ok().build();
    }
}
