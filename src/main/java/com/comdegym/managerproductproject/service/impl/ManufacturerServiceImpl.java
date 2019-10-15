package com.comdegym.managerproductproject.service.impl;

import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.repository.ManufacturerRepository;
import com.comdegym.managerproductproject.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Override
    public Iterable<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public void save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void remove(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
