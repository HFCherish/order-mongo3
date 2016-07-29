package com.thoughtworks.ketsu.Dao;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.UserMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;

public class UserDao implements UserMapper {

    private final MongoCollection userCollection;

    @Inject
    public UserDao(Jongo jongo) {
        userCollection = jongo.getCollection("users");
    }

    @Override
    public User save(Map<String, Object> info) {
        userCollection.insert(info);
        return SafeInjector.injectMembers(userCollection.findOne().as(User.class));
    }

    @Override
    public User findById(String userId) {
        User user = userCollection.findOne(new ObjectId(userId)).as(User.class);
        return SafeInjector.injectMembers(user);
    }
}
