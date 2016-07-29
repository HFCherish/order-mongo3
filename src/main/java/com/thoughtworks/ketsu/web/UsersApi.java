package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("users")
public class UsersApi {
    @Context
    UserRepository userRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Map<String, Object> info,
                             @Context Routes routes) {
        Object name = info.get("name");
        if(name == null || !name.toString().matches("^[a-zA-Z\\d]+$")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new HashMap() {{
                put("field", "name");
                put("message", "name can not be empty and must be composed of letters and numbers.");
            }}).build();
        }
        return Response.created(routes.userUrl(userRepository.save(info).getId())).build();
    }

    @Path("{id}")
    public UserApi getUser(@PathParam("id") String id) {
        return userRepository.findById(id)
                .map(UserApi::new)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }


}
