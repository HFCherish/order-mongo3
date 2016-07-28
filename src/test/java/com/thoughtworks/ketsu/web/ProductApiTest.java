package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{

    private final String productBaseUrl = "/products";

    @Test
    public void should_create_product() {
        Response response = post(productBaseUrl, productJsonForTest());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(productBaseUrl));
    }
}