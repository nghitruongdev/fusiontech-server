package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.User;
import com.vnco.fusiontech.product.repository.UserRepository;
import com.vnco.fusiontech.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    // them moi user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // cap nhat user theo id
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Integer id,@RequestBody User user) {
        return userService.updateUser(user);
    }

    // xoa user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
