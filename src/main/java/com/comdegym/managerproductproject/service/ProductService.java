package com.comdegym.managerproductproject.service;

import com.comdegym.managerproductproject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductService {
    Iterable<Product> findAll();

    Page<Product> findAll(Pageable pageInfo);

//    Iterable<Product> search(String keyword);

    Page<Product> search(String keyword, Pageable pageInfo);

    Optional<Product> findById(Long id);

    void save(Product product);

    void remove(Long id);
}
