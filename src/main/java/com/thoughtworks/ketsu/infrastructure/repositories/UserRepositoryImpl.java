package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepositry;

import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepositry {
    @Override
    public User save(Map<String, Object> info) {
        return null;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(new User());
    }
}
