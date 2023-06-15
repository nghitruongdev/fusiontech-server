package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findAllByUserId(UUID uid);
    
    boolean existsByIdAndUserId(Long id, UUID userId);
    
}
