package com.thoughtworks.ketsu.domain.products;

import java.util.Map;
import java.util.Optional;

public interface ProductRepository {
    Product save(Map<String, Object> info);

    Optional<Product> findById(String prodId);
}
