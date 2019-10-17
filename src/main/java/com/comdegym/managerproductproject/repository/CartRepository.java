package com.comdegym.managerproductproject.repository;

import com.comdegym.managerproductproject.model.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {
}
