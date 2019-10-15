package com.comdegym.managerproductproject.repository;

import com.comdegym.managerproductproject.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
