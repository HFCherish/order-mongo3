package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.ProductMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Inject
    ProductMapper productMapper;

    @Override
    public Product save(Map<String, Object> info) {
        return productMapper.save(info);
    }

    @Override
    public Optional<Product> findById(String prodId) {
        return Optional.ofNullable(productMapper.findById(prodId));
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
}
