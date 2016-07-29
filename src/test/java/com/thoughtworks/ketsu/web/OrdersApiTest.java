package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
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
public class OrdersApiTest extends ApiSupport {
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
        assertThat(response.getLocation().toString().matches(".*[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_create_given_incomplete_user_info() {
        Map<String, Object> info = orderJsonForTest(product.getId());
        //name empty
        info.remove("name");

        Response response = post(orderBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_400_when_create_given_incomplete_order_item_info() {
        Map<String, Object> info = orderJsonForTest(new ObjectId().toString());

        Response response = post(orderBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_some_order() {
        Map<String, Object> info = orderJsonForTest(new ObjectId().toString());
        Order save = user.placeOrder(info);

        Response response = get(orderBaseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("uri").toString(), containsString(orderBaseUrl + "/" + save.getId()));
        assertThat(fetchedInfo.get("name"), is(info.get("name")));
        assertThat(fetchedInfo.get("address"), is(info.get("address")));
        assertThat(fetchedInfo.get("phone"), is(info.get("phone")));
        assertThat(fetchedInfo.get("created_at").toString(), is(save.getCreatedAt().toString()));

    }
}