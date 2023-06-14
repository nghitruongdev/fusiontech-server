package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite save(Favorite favorite);

    Favorite update(Favorite favorite);

    void delete(int id);

    Favorite findById(int id);

    List<Favorite> findAll();
}
