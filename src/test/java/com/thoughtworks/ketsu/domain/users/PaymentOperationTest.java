package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class PaymentOperationTest {

    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;

    private Order order;

    @Before
    public void setUp() throws Exception {
        User user = userRepository.save(userJsonForTest(USER_NAME));
        order = user.placeOrder(orderJsonForTest(productRepository.save(productJsonForTest()).getId()));
    }

    @Test
    public void should_pay_and_get() {
        Payment pay = order.pay(paymentJsonForTest());
        Optional<Payment> fetched = order.getPayment();

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().order.getId(), is(pay.order.getId()));
    }
}
