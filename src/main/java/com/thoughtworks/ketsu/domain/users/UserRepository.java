package com.thoughtworks.ketsu.domain.users;

import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    User save(Map<String, Object> info);

    Optional<User> findById(String userId);
}
