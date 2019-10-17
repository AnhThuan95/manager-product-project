package com.comdegym.managerproductproject.service.impl;

import com.comdegym.managerproductproject.model.Cart;
import com.comdegym.managerproductproject.repository.CartRepository;
import com.comdegym.managerproductproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Page<Cart> findAll(Pageable pageInfo) {
        return cartRepository.findAll(pageInfo);
    }

    @Override
    public Page<Cart> search(String keyword, Pageable pageInfo) {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {
        cartRepository.deleteById(id);
    }
}
