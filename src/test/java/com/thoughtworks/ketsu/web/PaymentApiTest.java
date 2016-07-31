package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport {

    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;

    private String paymentBaseUrl;
    private Order order;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        User user = userRepository.save(userJsonForTest(USER_NAME));
        order = user.placeOrder(orderJsonForTest(productRepository.save(productJsonForTest()).getId()));
        paymentBaseUrl = "/users/" + user.getId() + "/orders/" + order.getId() + "/payment";
    }

    @Test
    public void should_pay() {
        Response response = post(paymentBaseUrl, paymentJsonForTest());

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_400_when_pay_given_incomplete_input() {

        Map<String, Object> info = paymentJsonForTest();
        //type empty
        info.remove("pay_type");

        Response response = post(paymentBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_payment() {


    }
}