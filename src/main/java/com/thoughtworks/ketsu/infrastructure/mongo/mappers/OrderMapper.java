package com.thoughtworks.ketsu.infrastructure.mongo.mappers;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    Order save(Map<String, Object> info, String userId);

    Order findById(String orderId);

    List<Order> findAllOf(String userId);

    Payment pay(Map<String, Object> info, String orderId);

    Payment findPayment(String orderId);
}
