package com.thoughtworks.ketsu.domain.users;

import java.util.Map;
import java.util.Optional;

public interface UserRepositry {
    User save(Map<String, Object> info);

    Optional<User> findById(String userId);
}
