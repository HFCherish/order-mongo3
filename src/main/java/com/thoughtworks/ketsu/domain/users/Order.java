package com.thoughtworks.ketsu.domain.users;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

public class Order {
    @MongoId
    @MongoObjectId
    String id;
    String name;
    String address;
    String phone;
    List<OrderItem> orderItems;

    public String getId() {
        return id;
    }
}
