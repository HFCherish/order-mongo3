package com.thoughtworks.ketsu.domain.products;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.HashMap;
import java.util.Map;

public class Product implements Record{
    @MongoId
    @MongoObjectId
    protected String id;
    protected String name;
    protected String description;
    protected double price;

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.productUrl(id));
            put("_id", id);
            put("name", name);
            put("description", description);
            put("price", price);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
