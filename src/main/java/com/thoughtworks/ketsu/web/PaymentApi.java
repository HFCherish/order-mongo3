package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.web.validators.NullFieldValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

public class PaymentApi {
    private Order order;

    public PaymentApi(Order order) {
        this.order = order;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(Map<String, Object> info) {
//        Map<String, List> nullFieldsMap = new NullFieldValidator().getNullFieldsMap(Arrays.asList("pay_type", "amount"), info);
//        if(nullFieldsMap != null) {
//            return Response.status(400).entity(nullFieldsMap).build();
//        }
        new NullFieldValidator().validate(Arrays.asList("pay_type", "amount"),info);
        order.pay(info);
        return Response.created(URI.create("")).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPay() {
        return order.getPayment()
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}
