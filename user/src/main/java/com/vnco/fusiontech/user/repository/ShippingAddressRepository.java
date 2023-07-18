package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findAllByUserId(@Param("uid") Long uid);

    boolean existsByIdAndUserId(@Param("id") Long id, @Param ("uid") Long userId);

    @Query("from ShippingAddress s WHERE s.user.id =:uid AND s.user.defaultAddress.id=s.id")
    Optional<ShippingAddress> findDefaultShippingAddressByUserId(@Param("uid") Long uid);
    
    @RestResource(path = "/many")
    List<ShippingAddress> findAllByIdIn(@Param ("ids") List<Long> ids);
}
