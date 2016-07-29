package com.thoughtworks.ketsu.infrastructure.mongo.mappers;

import com.thoughtworks.ketsu.domain.products.Product;

import java.util.Map;

public interface ProductMapper {
    Product save(Map<String, Object> info);

    Product findById(String prodId);
}
