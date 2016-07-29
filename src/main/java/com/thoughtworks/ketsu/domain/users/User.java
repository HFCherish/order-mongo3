package com.thoughtworks.ketsu.domain.users;

import com.google.inject.Injector;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.jongo.Jongo;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User implements Record{
    @MongoId
    @MongoObjectId
    protected String id;
    protected String name;

    @Inject
    OrderMapper orderMapper;

    @Inject
    Jongo jongo;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.userUrl(id));
            put("_id", id);
            put("name", name);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Order placeOrder(Map<String, Object> info) {
        return orderMapper.save(info, id);
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orderMapper.findById(orderId));
    }
}
