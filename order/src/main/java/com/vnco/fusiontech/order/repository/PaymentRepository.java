package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    @RestResource(path = "many", rel = "many")
    List<Payment> findAllByIdIn(@Param("ids") List<Long> ids);
}
