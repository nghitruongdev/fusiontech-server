package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.common.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
