package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;

import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product save(Map<String, Object> info) {
        return null;
    }

    @Override
    public Optional<Product> findById(String prodId) {
        return Optional.ofNullable(new Product());
    }
}
