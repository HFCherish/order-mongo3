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

import java.util.List;
import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;
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
    private Map<String, Object> prodInfo;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = userRepository.save(userJsonForTest(USER_NAME));
        prodInfo = productJsonForTest();
        product = productRepository.save(prodInfo);
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
        Map<String, Object> info = orderJsonForTest(product.getId());
        Order save = user.placeOrder(info);

        Response response = get(orderBaseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        verifyBaiscOrderInfo(info, save, fetchedInfo);

        List<Map> order_items = (List<Map>) fetchedInfo.get("order_items");
        assertThat(order_items.size(), is(1));
        Map savedItem = (Map) order_items.get(0);
        assertThat(order_items.get(0).get("uri").toString(), containsString("/products/" + savedItem.get("product_id")));
        assertThat(order_items.get(0).get("product_id"), is(savedItem.get("product_id")));
        assertThat(order_items.get(0).get("quantity"), is(savedItem.get("quantity")));
        assertThat(order_items.get(0).get("amount"), is(product.getPrice()));

    }

    private void verifyBaiscOrderInfo(Map<String, Object> info, Order save, Map fetchedInfo) {
        assertThat(fetchedInfo.get("uri").toString(), containsString(orderBaseUrl + "/" + save.getId()));
        assertThat(fetchedInfo.get("name"), is(info.get("name")));
        assertThat(fetchedInfo.get("address"), is(info.get("address")));
        assertThat(fetchedInfo.get("phone"), is(info.get("phone")));
        assertThat((double)fetchedInfo.get("total_price"), is(ITEM_QUANTITY * PROD_PRICE));
        assertThat(fetchedInfo.get("created_at").toString(), is(new ObjectId(save.getId()).getDate().toString()));
    }

    @Test
    public void should_404_when_get_given_not_exist() {

        Response response = get(orderBaseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_get_all_orders() {

        Map<String, Object> info = orderJsonForTest(product.getId());
        Order save = user.placeOrder(info);

        Response response = get(orderBaseUrl);

        assertThat(response.getStatus(), is(200));
        List<Map> fetchedInfo = response.readEntity(List.class);
        assertThat(fetchedInfo.size(), is(1));
        verifyBaiscOrderInfo(info, save, fetchedInfo.get(0));
        assertThat(fetchedInfo.get(0).get("order_items"), is(nullValue()));
    }
}