package com.thoughtworks.ketsu.Dao;

import com.google.common.collect.FluentIterable;
import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.ProductMapper;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ProductDao implements ProductMapper {

    private final MongoCollection prodCollection;

    @Inject
    public ProductDao(Jongo jongo) {
        prodCollection = jongo.getCollection("products");
    }

    @Override
    public Product save(Map<String, Object> info) {
        prodCollection.insert(info);
        return prodCollection.findOne().as(Product.class);
    }

    @Override
    public Product findById(String prodId) {
        return prodCollection.findOne(new ObjectId(prodId)).as(Product.class);
    }

    @Override
    public List<Product> findAll() {
        return FluentIterable.from(prodCollection.find().as(Product.class)).toList();
    }
}
