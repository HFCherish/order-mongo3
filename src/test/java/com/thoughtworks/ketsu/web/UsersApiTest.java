package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import static com.thoughtworks.ketsu.support.TestHelper.userJsonForTest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class UsersApiTest extends ApiSupport{

    private String userBaseUrl = "/users";

    @Test
    public void should_register() {
        Response response = post(userBaseUrl, userJsonForTest());

        assertThat(response.getStatus(), is(201));

    }
}