package com.comdegym.managerproductproject.service;

import com.comdegym.managerproductproject.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerService {
    Iterable<Manufacturer> findAll();

    Optional<Manufacturer> findById(Long id);

    void save(Manufacturer manufacturer);

    void remove(Long id);
}
