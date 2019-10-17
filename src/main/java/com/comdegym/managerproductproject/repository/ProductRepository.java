package com.comdegym.managerproductproject.repository;

import com.comdegym.managerproductproject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
//    Iterable<Product> findAllByNameContainsOrDescriptionContains(String name, String description);

    Page<Product> findAllByNameContainsOrDescriptionContains(String name, String description, Pageable pageable);
}
