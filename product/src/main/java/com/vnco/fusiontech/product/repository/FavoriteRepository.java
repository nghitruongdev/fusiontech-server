package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

}
