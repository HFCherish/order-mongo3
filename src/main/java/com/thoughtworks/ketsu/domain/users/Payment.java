package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
    @JsonProperty("pay_type")
    PayType payType;
    @JsonProperty("amount")
    double amount;
    Order order;

    public Payment(PayType payType, double amount, Order order) {
        this.payType = payType;
        this.amount = amount;
        this.order = order;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("order_uri", routes.orderUrl(order.userId, order.id));
            put("uri", routes.paymentUrl(order.userId, order.id));
            put("amount", amount);
            put("pay_type", payType.name());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
