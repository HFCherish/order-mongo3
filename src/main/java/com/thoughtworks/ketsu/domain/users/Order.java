package com.thoughtworks.ketsu.domain.users;

import java.util.List;

public class Order {
    String id;
    String name;
    String address;
    String phone;
    List<OrderItem> orderItems;

    public String getId() {
        return id;
    }
}
