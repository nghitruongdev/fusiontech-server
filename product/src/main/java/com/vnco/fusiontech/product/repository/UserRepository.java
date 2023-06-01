package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Review;
import com.vnco.fusiontech.product.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
