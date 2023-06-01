package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);

}
