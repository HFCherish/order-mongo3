package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class OrderItem implements Record {
    @JsonProperty("product_id")
    String prodId;
    int quantity;
    double amount;

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.productUrl(prodId));
            put("product_id", prodId);
            put("quantity", quantity);
            put("amount", amount);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
