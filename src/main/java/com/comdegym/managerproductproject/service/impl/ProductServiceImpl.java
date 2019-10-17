package com.comdegym.managerproductproject.service.impl;

import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.repository.ProductRepository;
import com.comdegym.managerproductproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageInfo) {
        return productRepository.findAll(pageInfo);
    }

//    @Override
//    public Iterable<Product> search(String keyword) {
//        return productRepository.findAllByNameContainsOrDescriptionContains(keyword, keyword);
//    }

    @Override
    public Page<Product> search(String keyword, Pageable pageInfo) {
        return productRepository.findAllByNameContainsOrDescriptionContains(keyword, keyword, pageInfo);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
}
