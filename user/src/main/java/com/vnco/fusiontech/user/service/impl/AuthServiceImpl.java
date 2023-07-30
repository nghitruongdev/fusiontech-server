package com.vnco.fusiontech.user.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordExistsException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.web.rest.request.FirebaseMapper;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static  FirebaseAuth auth (){
        return FirebaseAuth.getInstance();
    }
    private final FirebaseMapper mapper;
    @Override
    public UserRecord registerWithEmailProvider(UserRequest request) {
        try {
            var        user       = mapper.toFirebaseCreateRequest(request);
            return FirebaseAuth.getInstance().createUser(user);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
            return null;
        }
    }
    
    @Override
    public UserRecord registerWithGoogleProvider(String firebaseId) {
        try {
            return FirebaseAuth.getInstance().getUser(firebaseId);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
            return null;
        }
    }
    
    @Override
    public String setInitialClaims(Long id, String firebaseId) {
        try {
            auth().setCustomUserClaims(firebaseId, getInitialClaims(id));
            return FirebaseAuth.getInstance().createCustomToken(firebaseId, getInitialClaims(id));
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return null;
    }
    
    @Override
    public void updateProfile(User user, UserRequest request, String firebaseId) {
        var updateRequest = new UserRecord.UpdateRequest(firebaseId);
        var mapped = mapper.toFirebaseUpdateRequest(request,user, updateRequest);
        
        try {
            FirebaseAuth.getInstance().updateUser(mapped);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }
    
    @Override
    public void updatePassword(String password, String firebaseId) {
        UserRecord.UpdateRequest user = new UserRecord.UpdateRequest(firebaseId);
        if (password != null)
            throw new IllegalArgumentException("Invalid password: " + password);
        user.setPassword(password);
        try {
            FirebaseAuth.getInstance().updateUser(user);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }
    
    @Override
    public void deleteAccount(String firebaseId) {
        log.warn("About to delete user {}", firebaseId);
        try {
            FirebaseAuth.getInstance().deleteUser(firebaseId);
            log.trace("Successfully delete user {}", firebaseId);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }
    
    @Override
    public void setActiveAccount(String firebaseId, boolean isDisabled) {
        try {
            FirebaseAuth.getInstance().updateUser(new UserRecord.UpdateRequest(firebaseId).setDisabled(isDisabled));
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }
    
    @Override
    public List<UserRecord> findAll() {
        List<UserRecord> users   = new ArrayList<>(1000);
        boolean              hasNext = true;
        String token = null;
        try {
            do{
                var listPage =  FirebaseAuth.getInstance().listUsers(token);
                users.addAll(listPage.streamAll().toList());
                hasNext = listPage.hasNextPage();
                token = listPage.getNextPageToken();
            }while (hasNext);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return users;
    }
    
    private Map<String, Object> getInitialClaims(Long id) {
        return Map.of(AuthoritiesConstant.ROLE_NAME, List.of(AuthoritiesConstant.USER), "id", id);
    }
    
    private void handleFirebaseAuthException(FirebaseAuthException ex) throws RuntimeException{
        var authCode = ex.getAuthErrorCode()!= null? ex.getAuthErrorCode().name() : "";
        switch (ex.getErrorCode()){
            case ALREADY_EXISTS ->
                    throw new RecordExistsException(authCode);
            case NOT_FOUND ->
                    throw new RecordNotFoundException(authCode);
            case INVALID_ARGUMENT ->
                    throw new InvalidRequestException(authCode);
        }
        throw new RuntimeException(ex);
    }
}
