package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}

