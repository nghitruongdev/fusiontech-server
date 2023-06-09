package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.user.entity.Authority;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.AuthorityRepository;
import com.vnco.fusiontech.user.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepository repo;

//    @Override
//    public Authority addUserAuthority(User uid, String aName) {
//        Authority authority = new Authority(uid, aName);
//        if (aName.equalsIgnoreCase(authority.getRoleName())) {
//            log.info("Role name is valid");
//            repo.save(authority);
//        } else {
//            throw new RuntimeException("Role name is not valid");
//        }
//        return repo.save(authority);
//    }
}
