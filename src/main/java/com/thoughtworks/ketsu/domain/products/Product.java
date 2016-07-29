package com.thoughtworks.ketsu.domain.products;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Product {
    @MongoId
    @MongoObjectId
    protected String id;
    protected String name;
    protected String description;
    protected double price;
}
