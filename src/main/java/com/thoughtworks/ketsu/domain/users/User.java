package com.thoughtworks.ketsu.domain.users;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
    @MongoId
    @MongoObjectId
    protected String id;
    protected String name;

    public String getId() {
        return id;
    }
}
