package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Record{
    @MongoId
    @MongoObjectId
    String id;
    @JsonProperty("user_id")
    String userId;
    String name;
    String address;
    String phone;
    List<OrderItem> orderItems;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.orderUrl(userId, id));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("created_at", getCreatedAt().toString());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Date getCreatedAt() {
        return new ObjectId(id).getDate();
    }
}
