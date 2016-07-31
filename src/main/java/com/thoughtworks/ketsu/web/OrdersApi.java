package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.web.jersey.Routes;
import com.thoughtworks.ketsu.web.validators.OrderValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersApi {
    private User user;

    public OrdersApi(User user) {
        this.user = user;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(Map<String, Object> info,
                               @Context Routes routes,
                               @Context OrderValidator orderValidator) {
        Map<String, List> nullFields = orderValidator.getNullFieldsMap(info);
        if(nullFields != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(nullFields).build();
        }
        return Response.created(routes.orderUrl(user.getId(), user.placeOrder(info).getId())).build();
    }

    @Path("{id}")
    public OrderApi getOne(@PathParam("id") String id) {
        return user.findOrderById(id)
                .map(OrderApi::new)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getAll() {
        return new ArrayList<>();
    }

}
