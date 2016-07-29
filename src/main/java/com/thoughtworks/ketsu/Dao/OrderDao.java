package com.thoughtworks.ketsu.Dao;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;

public class OrderDao implements OrderMapper {

    private final MongoCollection orderCollection;

    @Inject
    public OrderDao(Jongo jongo) {
        orderCollection = jongo.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        orderCollection.save(info);
        return SafeInjector.injectMembers(orderCollection.findOne().as(Order.class));
    }

    @Override
    public Order findById(String orderId) {
        return SafeInjector.injectMembers(orderCollection.findOne(new ObjectId(orderId)).as(Order.class));
    }
}
