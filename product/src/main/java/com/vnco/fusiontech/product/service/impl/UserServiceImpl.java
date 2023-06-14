package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Favorite;
import com.vnco.fusiontech.product.repository.BrandRepository;
import com.vnco.fusiontech.product.repository.FavoriteRepository;
import com.vnco.fusiontech.product.service.BrandService;
import com.vnco.fusiontech.product.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite update(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }


    @Override
    public void delete(int id) {
        favoriteRepository.deleteById(id);
    }

    @Override
    public Favorite findById(int id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }
}
