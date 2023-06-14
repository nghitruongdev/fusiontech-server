package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.entity.Favorite;
import com.vnco.fusiontech.product.repository.FavoriteRepository;
import com.vnco.fusiontech.product.service.AttributeService;
import com.vnco.fusiontech.product.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteRestController {
    private final FavoriteService favoriteService;

    @GetMapping
    public List<Favorite> getAllFavorites() {
        return favoriteService.findAll();
    }

    @GetMapping("/{id}")
    public Favorite getFavoriteById(@PathVariable int id) {
        return favoriteService.findById(id);
    }

    @PostMapping
    public Favorite createFavorite(@RequestBody Favorite favorite) {
        return favoriteService.save(favorite);
    }

    @PutMapping("/{id}")
    public Favorite updateFavorite(@PathVariable("id") Integer id,@RequestBody Favorite favorite) {
        return favoriteService.update(favorite);
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable int id) {
        favoriteService.delete(id);
    }
}
