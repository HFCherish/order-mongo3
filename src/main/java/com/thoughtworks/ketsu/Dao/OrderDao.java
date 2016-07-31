package com.thoughtworks.ketsu.Dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.PayType;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.bson.types.ObjectId;
import org.jongo.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.jongo.Oid.withOid;

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

    @Override
    public Payment pay(Map<String, Object> info, String orderId) {
        orderCollection.update(withOid(orderId)).with("{$set: {payment: #}}", info);
        FindOne projection = orderCollection.findOne(withOid(orderId));
//        Order order = SafeInjector.injectMembers(projection.as(Order.class));
//        Payment as = projection.map(new ResultHandler<Payment>() {
//            @Override
//            public Payment map(DBObject result) {
//                Object payment = result.get("payment");
//                try {
//
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    Payment payment1 = objectMapper.readValue(payment.toString(), Payment.class);
//                    payment1.setOrder(order);
//                    return payment1;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
        return buildPayment(projection);
    }

    @Override
    public Payment findPayment(String orderId) {
        return buildPayment(orderCollection.findOne(withOid(orderId)));
    }

    private Payment buildPayment(FindOne findOne) {
        Map info = findOne.as(Map.class);
        Map payment = (Map)info.get("payment");
        if(payment == null) return null;

        return new Payment(PayType.valueOf(payment.get("pay_type").toString()),
                (double)payment.get("amount"),
                SafeInjector.injectMembers(findOne.as(Order.class)));
    }
}
