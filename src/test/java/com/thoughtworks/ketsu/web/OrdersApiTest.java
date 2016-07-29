package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport{
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;
    private String orderBaseUrl;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = userRepository.save(userJsonForTest(USER_NAME));
        product = productRepository.save(productJsonForTest());
        orderBaseUrl = "/users/" + user.getId() + "/orders";
    }

    @Test
    public void should_create_order() {
        Map<String, Object> info = orderJsonForTest(product.getId());

        Response response = post(orderBaseUrl, info);

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(orderBaseUrl));

    }
}