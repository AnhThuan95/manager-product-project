package com.comdegym.managerproductproject.service;

import com.comdegym.managerproductproject.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CartService {
    Iterable<Cart> findAll();

    Page<Cart> findAll(Pageable pageInfo);

    Page<Cart> search(String keyword, Pageable pageInfo);

    Optional<Cart> findById(Long id);

    void save(Cart cart);

    void remove(Long id);
}
