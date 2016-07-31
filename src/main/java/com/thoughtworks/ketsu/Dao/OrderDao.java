package com.thoughtworks.ketsu.Dao;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao implements OrderMapper {

    private final MongoCollection orderCollection;

    @Inject
    ProductDao productDao;

    @Inject
    public OrderDao(Jongo jongo) {
        orderCollection = jongo.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        for(Map item: (List<Map>)info.get("order_items")) {
            item.put("amount", productDao.getPriceOf(item.get("product_id").toString()));
        }
        orderCollection.save(info);
        return SafeInjector.injectMembers(orderCollection.findOne().as(Order.class));
    }

    @Override
    public Order findById(String orderId) {
        return SafeInjector.injectMembers(orderCollection.findOne(new ObjectId(orderId)).as(Order.class));
    }

    @Override
    public List<Order> findAllOf(String userId) {
        MongoCursor<Order> mongoCursor = orderCollection.find("{user_id:#}", userId).as(Order.class);
        List<Order> orders = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            orders.add(SafeInjector.injectMembers(mongoCursor.next()));
        }
        return orders;
    }
}
