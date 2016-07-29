package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.USER_NAME;
import static com.thoughtworks.ketsu.support.TestHelper.userJsonForTest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(DatabaseTestRunner.class)
public class UserRepositryTest {
    @Inject
    UserRepositry userRepositry;

    @Test
    public void should_save_and_get_user() {
        User save = userRepositry.save(userJsonForTest(USER_NAME));
        Optional<User> fetched = userRepositry.findById(save.id);

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().id, is(save.id));

    }
}