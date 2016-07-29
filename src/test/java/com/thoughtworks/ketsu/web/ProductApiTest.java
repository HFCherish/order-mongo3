package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{

    @Inject
    ProductRepository productRepository;

    private final String productBaseUrl = "/products";

    @Test
    public void should_create_product() {
        Response response = post(productBaseUrl, productJsonForTest());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(productBaseUrl));
        assertThat(response.getLocation().toString().matches(".*/[\\w\\W\\d]+$"), is(true));
    }

    @Test
    public void should_400_when_create_given_incomplete_input() {
        Map<String, Object> info = productJsonForTest();
        //name empty
        info.remove("name");

        Response response = post(productBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_some_product() {
        Map<String, Object> info = productJsonForTest();
        Product save = productRepository.save(info);

        Response response = get(productBaseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        verifyProductInfo(info, save, fetchedInfo);
    }

    private void verifyProductInfo(Map<String, Object> info, Product save, Map fetchedInfo) {
        assertThat(fetchedInfo.get("_id"), is(save.getId()));
        assertThat(fetchedInfo.get("uri").toString(), containsString(productBaseUrl + "/" + save.getId()));
        assertThat(fetchedInfo.get("name"), is(info.get("name")));
        assertThat(fetchedInfo.get("description"), is(info.get("description")));
        assertThat(fetchedInfo.get("price"), is(info.get("price")));
    }

    @Test
    public void should_404_when_get_given_not_exist() {
        Response response = get(productBaseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_get_all_products() {
        Map<String, Object> info = productJsonForTest();
        Product save = productRepository.save(info);

        Response response = get(productBaseUrl);

        assertThat(response.getStatus(), is(200));
        List<Map> list = response.readEntity(List.class);
        assertThat(list.size(), is(1));
        verifyProductInfo(info, save, list.get(0));
    }
}